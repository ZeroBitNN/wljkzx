package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DemandDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TDemand;
import pageModel.DataGrid;
import pageModel.Demand;
import pageModel.User;
import service.DemandServiceI;

@Service(value = "demandService")
public class DemandServiceImpl implements DemandServiceI {
	private static final Logger logger = Logger.getLogger(DemandServiceImpl.class);
	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	private DemandDaoI demandDao;

	public DemandDaoI getDemandDao() {
		return demandDao;
	}

	@Autowired
	public void setDemandDao(DemandDaoI demandDao) {
		this.demandDao = demandDao;
	}

	@Override
	public DataGrid<Demand> getDataGrid(Demand demand) {
		DataGrid<Demand> dg = new DataGrid<Demand>();
		String hql = "from TDemand t where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();

		if (demand.getContent() != null && !demand.getContent().trim().equals("")) {
			hql += " and t.content like :content";
			params.put("content", demand.getContent());
		}

		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(demandDao.count(totalHql, params));

		// 排序条件
		if (demand.getSort() != null && demand.getOrder() != null) {
			hql += " order by " + demand.getSort() + " " + demand.getOrder();
		}

		List<TDemand> tList = demandDao.find(hql, params, demand.getPage(), demand.getRows());
		List<Demand> dList = new ArrayList<Demand>();
		if (tList != null && tList.size() > 0) {
			for (TDemand t : tList) {
				Demand d = new Demand();
				BeanUtils.copyProperties(t, d);
				d.setRecorder(t.getTAccount().getUsername());
				dList.add(d);
			}
		}
		dg.setRows(dList);

		return dg;
	}

	@Override
	public List<Demand> getListForShow() {
		List<Demand> dList = new ArrayList<Demand>();

		String hql = "from TDemand t where t.isshow='是'";
		List<TDemand> tList = demandDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TDemand t : tList) {
				Demand d = new Demand();
				BeanUtils.copyProperties(t, d);
				d.setRecorder(t.getTAccount().getUsername());
				d.setContent(d.getContent().replaceAll("\r\n", "<br>"));
				dList.add(d);
			}
		}

		return dList;
	}

	@Override
	public Demand save(Demand demand, User user) {
		TDemand t = new TDemand();
		BeanUtils.copyProperties(demand, t);

		TAccount tAcc = userDao.getForId(TAccount.class, user.getId());
		t.setTAccount(tAcc);
		demand.setRecorder(t.getTAccount().getUsername());

		t.setCreatetime(new Date());
		demand.setCreatetime(t.getCreatetime());

		if (demand.getIsshow() == null || demand.getIsshow().trim().equals("")) {
			t.setIsshow("否");
			demand.setIsshow(t.getIsshow());
		}

		demandDao.save(t);

		return demand;
	}

	@Override
	public Demand update(Demand demand, User user) {
		TDemand t = demandDao.getForId(TDemand.class, demand.getId());
		BeanUtils.copyProperties(demand, t, new String[] { "id", "createtime" });

		t.setModifytime(new Date());
		if (demand.getIsshow() == null || demand.getIsshow().trim().equals("")) {
			t.setIsshow("否");
		}
		BeanUtils.copyProperties(t, demand);

		TAccount tAcc = userDao.getForId(TAccount.class, user.getId());
		t.setTAccount(tAcc);
		demand.setRecorder(t.getTAccount().getUsername());

		return demand;
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TDemand t where t.id='" + id + "'";
		demandDao.executeHql(hql);
	}
}
