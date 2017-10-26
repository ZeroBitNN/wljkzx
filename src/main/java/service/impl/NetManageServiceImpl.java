package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.NetManageDaoI;
import model.TNetmanage;
import pageModel.DataGrid;
import pageModel.NetManage;
import service.NetManageServiceI;

@Service(value = "netManageService")
public class NetManageServiceImpl implements NetManageServiceI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NetManageServiceImpl.class);

	private NetManageDaoI netManageDao;

	public NetManageDaoI getNetManageDao() {
		return netManageDao;
	}

	@Autowired
	public void setNetManageDao(NetManageDaoI netManageDao) {
		this.netManageDao = netManageDao;
	}

	@Override
	public NetManage save(NetManage netManage) {
		TNetmanage tNetManage = new TNetmanage();
		BeanUtils.copyProperties(netManage, tNetManage);
		tNetManage.setId(UUID.randomUUID().toString());
		netManageDao.save(tNetManage);
		BeanUtils.copyProperties(tNetManage, netManage);
		return netManage;
	}

	@Override
	public DataGrid<NetManage> getAll(NetManage netManage) {
		DataGrid<NetManage> dg = new DataGrid<NetManage>();
		String hql = "from TNetmanage t";
		// 判断是否按系统名称查询
		if (netManage.getName() != null && !netManage.getName().trim().equals("")) {
			hql += " where upper(t.name) like '%" + netManage.getName().trim().toUpperCase() + "%'";
		}
		// 判断是否按系统名称排序
		if (netManage.getSort()!=null){
			hql += " order by " + netManage.getSort() + " " + netManage.getOrder();
		}

		// 查询数据
		logger.info(hql);
		List<TNetmanage> list = netManageDao.find(hql, netManage.getPage(), netManage.getRows());
		List<NetManage> netManageList = new ArrayList<NetManage>();
		if (list != null && list.size() > 0) {
			for (TNetmanage t : list) {
				NetManage n = new NetManage();
				BeanUtils.copyProperties(t, n);
				netManageList.add(n);
			}
		}
		dg.setRows(netManageList);

		// 查询结果记录数
		String totalHql = "select count(*) " + hql;
		logger.info(totalHql);
		dg.setTotal(netManageDao.count(totalHql));
		logger.info("共找到" + dg.getTotal() + "条记录");

		return dg;
	}

	@Override
	public void delete(String id) {
		// 网管密码模块删除功能
		String hql = "delete from TNetmanage t where t.id='" + id + "'";
		netManageDao.executeHql(hql);
		
	}

	@Override
	public NetManage edit(NetManage netManage) {
		// 网管密码模块编辑功能
		TNetmanage t = netManageDao.getForId(TNetmanage.class, netManage.getId());
		BeanUtils.copyProperties(netManage, t);
		netManageDao.saveOrUpdate(t);
		return netManage;
	}

}
