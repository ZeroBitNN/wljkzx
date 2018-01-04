package service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PerfNumDaoI;
import dao.PerfParamDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TPerfNum;
import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.PerfLevel;
import pageModel.PerfParam;
import service.PerfParamServiceI;
import util.StringUtil;

@Service(value = "perfParamService")
public class PerfParamServiceImpl implements PerfParamServiceI {
	private static final Logger logger = Logger.getLogger(PerfParamServiceImpl.class);

	private PerfParamDaoI perfParamDao;

	public PerfParamDaoI getPerfParamDao() {
		return perfParamDao;
	}

	@Autowired
	public void setPerfParamDao(PerfParamDaoI perfParamDao) {
		this.perfParamDao = perfParamDao;
	}

	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	private PerfNumDaoI perfNumDao;

	public PerfNumDaoI getPerfNumDao() {
		return perfNumDao;
	}

	@Autowired
	public void setPerfNumDao(PerfNumDaoI perfNumDao) {
		this.perfNumDao = perfNumDao;
	}

	@Override
	public DataGrid<PerfParam> getParams() {
		double sumValue = 0.0;
		double sumPercent = 0;
		List<PerfParam> footer = new ArrayList<PerfParam>();

		DataGrid<PerfParam> dg = new DataGrid<PerfParam>();
		String hql = "from TPerfParam t where type='类目' order by name";

		String totalHql = "select count(*) " + hql;
		dg.setTotal(perfParamDao.count(totalHql));

		List<TPerfParam> tList = perfParamDao.find(hql);
		List<PerfParam> pList = new ArrayList<PerfParam>();
		if (tList != null && tList.size() > 0) {
			for (TPerfParam t : tList) {
				if (t.getValue() != null) {
					sumValue += t.getValue();
				}
				if (t.getPercent() != null) {
					sumPercent += t.getPercent().doubleValue();
				}
				PerfParam p = new PerfParam();
				BeanUtils.copyProperties(t, p);
				if (t.getTPerfParam() != null && t.getTPerfParam().getId() != null
						&& !t.getTPerfParam().getId().equals("")) {
					p.setPid(t.getTPerfParam().getId());
					p.set_parentId(t.getTPerfParam().getId());
				}
				pList.add(p);
			}
		}
		dg.setRows(pList);

		PerfParam sumP = new PerfParam();
		sumP.setName("合计");
		sumP.setValue(sumValue / 2);
		sumP.setPercent(new BigDecimal(sumPercent));
		sumP.setPid("footer");
		footer.add(sumP);
		dg.setFooter(footer);

		return dg;
	}

	@Override
	public void addOrUpdate(PerfParam perfParam) {
		/**
		 * [id=7a8e9bd8-8414-8e5f-d2cd-54a4d559f9ec, name=新增类别, percent=null,
		 * value=null, describe=, type=null, pid=item2, _parentId=item2]
		 */
		TPerfParam t = perfParamDao.getForId(TPerfParam.class, perfParam.getId());
		if (t == null) {
			logger.info("新增类目");
			t = new TPerfParam();
			BeanUtils.copyProperties(perfParam, t);
			t.setTPerfParam(perfParamDao.getForId(TPerfParam.class, perfParam.getPid()));
			t.setType("类目");
			perfParamDao.save(t);
			// 为每个人员新增类目
			String userHql = "from TAccount t where t.username<>'admin'";
			List<TAccount> userList = userDao.find(userHql);
			if (userList != null && userList.size() > 0) {
				for (TAccount user : userList) {
					TPerfNum tp = new TPerfNum();
					tp.setId(UUID.randomUUID().toString());
					tp.setTAccount(user);
					tp.setTPerfParam(t);
					tp.setPerfdate(StringUtil.dateToYMString(new Date()));
					tp.setItemgroup(t.getTPerfParam().getName());
					perfNumDao.save(tp);
				}
			}
		} else {
			logger.info("修改类目");
			BeanUtils.copyProperties(perfParam, t, new String[] { "id", "type" });
			if (perfParam.getPid() != null && !perfParam.getPid().equals("")) {
				t.setTPerfParam(perfParamDao.getForId(TPerfParam.class, perfParam.getPid()));
			}
		}
	}

	@Override
	public void delete(PerfParam perfParam) throws Exception {
		/**
		 * [id=a44862ac-dee8-5cd9-c978-23032277a6c1, name=新增子类, percent=null,
		 * value=null, describe=, type=null, pid=item1, _parentId=item1]
		 */
		try {
			if (perfParam.getPid() != null && !perfParam.getPid().equals("")) {
				TPerfParam t = perfParamDao.getForId(TPerfParam.class, perfParam.getId());
				perfParamDao.delete(t);
			} else {
				throw new Exception("不允许删除初始化类目");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	@Override
	public PerfLevel getLevel() {
		/**
		 * Class cls = e.getClass(); Field[] fields = cls.getDeclaredFields();
		 * for(int i=0; i<fields.length; i++){ Field f = fields[i];
		 * f.setAccessible(true); System.out.println("属性名:" + f.getName() +
		 * " 属性值:" + f.get(e)); }
		 */
		PerfLevel p = new PerfLevel();
		Field[] fields = p.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			String hql = "from TPerfParam t where id='" + f.getName() + "'";
			f.setAccessible(true);
			TPerfParam t = perfParamDao.get(hql);
			if (t != null) {
				try {
					if (t.getPercent() != null) {
						f.set(p, t.getPercent().toString());
					} else {
						f.set(p, 0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return p;
	}

	@Override
	public void saveLevel(PerfLevel perfLevel) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = perfLevel.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			String hql = "from TPerfParam t where id='" + f.getName() + "'";
			TPerfParam t = perfParamDao.get(hql);
			if (t != null) {
				t.setPercent(new BigDecimal(f.get(perfLevel).toString()));
			}
			perfParamDao.save(t);
		}
	}

}
