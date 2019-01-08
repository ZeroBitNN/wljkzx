package service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.KbmsUserDaoI;
import kmbsModel.KbmsUser;
import model.TKbmsUser;
import pageModel.DataGrid;
import service.KbmsUserServiceI;
import util.Encrypt;

@Service(value = "kbmsUserService")
public class KbmsUserServiceImpl implements KbmsUserServiceI {
	private static final Logger logger = Logger.getLogger(KbmsUserServiceImpl.class);
	private KbmsUserDaoI kbmsUserDao;

	public KbmsUserDaoI getKbmsUserDao() {
		return kbmsUserDao;
	}

	@Autowired
	public void setKbmsUserDao(KbmsUserDaoI kbmsUserDao) {
		this.kbmsUserDao = kbmsUserDao;
	}

	// 日期显示格式
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public DataGrid<KbmsUser> getUser(KbmsUser kbmsUser) {
		DataGrid<KbmsUser> dg = new DataGrid<KbmsUser>();
		String hql = "from TKbmsUser t where t.username<>'admin'";
		// 判断是否按用户名查询
		if (kbmsUser.getUsername() != null && !kbmsUser.getUsername().trim().equals("")) {
			hql += " and t.username like '%" + kbmsUser.getUsername() + "%'";
		}
		// 查询总数
		String totalHql = "select count(*) " + hql;
		// 判断是否排序
		if (kbmsUser.getSort() != null) {
			hql += " order by " + kbmsUser.getSort() + " " + kbmsUser.getOrder();
		}

		// 执行查询
		logger.info("===查询用户===" + hql);
		List<TKbmsUser> tkuList = kbmsUserDao.find(hql, kbmsUser.getPage(), kbmsUser.getRows());
		List<KbmsUser> kuList = new ArrayList<KbmsUser>();
		if (tkuList != null && tkuList.size() > 0) {
			for (TKbmsUser tku : tkuList) {
				KbmsUser ku = new KbmsUser();
				BeanUtils.copyProperties(tku, ku);
				ku.setCreatetime(df.format(tku.getCreatetime()));
				if (tku.getModifytime() != null) {
					ku.setModifytime(df.format(tku.getModifytime()));
				}
				kuList.add(ku);
			}
		}
		dg.setRows(kuList);
		dg.setTotal(kbmsUserDao.count(totalHql));

		return dg;
	}

	@Override
	public KbmsUser save(KbmsUser kbmsUser) {
		TKbmsUser tku = new TKbmsUser();
		tku.setUsername(kbmsUser.getUsername());
		tku.setId(UUID.randomUUID().toString());
		tku.setPwd(Encrypt.e(kbmsUser.getPwd()));
		tku.setCreatetime(new Date());
		kbmsUserDao.save(tku);

		BeanUtils.copyProperties(tku, kbmsUser);
		kbmsUser.setCreatetime(df.format(tku.getCreatetime()));
		return kbmsUser;
	}

	@Override
	public KbmsUser edit(KbmsUser kbmsUser) {
		String newPwd = kbmsUser.getPwd();
		TKbmsUser tku = kbmsUserDao.getForId(TKbmsUser.class, kbmsUser.getId());
		String oldPwd = tku.getPwd();
		BeanUtils.copyProperties(kbmsUser, tku, new String[] { "pwd", "createtime", "modifytime" });
		try {
			tku.setModifytime(df.parse(kbmsUser.getModifytime()));
		} catch (ParseException e) {
			logger.info("字符串转换日期错误！");
		}

		if (!newPwd.equals(oldPwd)) {
			logger.info("修改 " + kbmsUser.getUsername() + " 用户密码！");
			tku.setPwd(Encrypt.e(newPwd));
			BeanUtils.copyProperties(tku, kbmsUser, new String[] { "createtime", "modifytime" });
		}

		return kbmsUser;
	}

	@Override
	public void delete(KbmsUser kbmsUser) {
		TKbmsUser tku = kbmsUserDao.getForId(TKbmsUser.class, kbmsUser.getId());
		kbmsUserDao.delete(tku);
	}

}
