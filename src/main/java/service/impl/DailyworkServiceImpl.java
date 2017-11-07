package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DailyworkDaoI;
import dao.DailyworkDetailsDaoI;
import dao.OrderCategoryDaoI;
import dao.UserDaoI;
import model.SessionInfo;
import model.TAccount;
import model.TDailywork;
import model.TDailyworkDetails;
import model.TOrderCategory;
import pageModel.Dailywork;
import pageModel.DailyworkDetails;
import pageModel.DataGrid;
import service.DailyworkServiceI;

@Service(value = "dailyworkService")
public class DailyworkServiceImpl implements DailyworkServiceI {
	private static final Logger log = Logger.getLogger(DailyworkServiceImpl.class);

	private DailyworkDaoI dailyworkDao;

	public DailyworkDaoI getDailyworkDao() {
		return dailyworkDao;
	}

	@Autowired
	public void setDailyworkDao(DailyworkDaoI dailyworkDao) {
		this.dailyworkDao = dailyworkDao;
	}

	private DailyworkDetailsDaoI dailyworkDetailsDao;

	public DailyworkDetailsDaoI getDailyworkDetailsDao() {
		return dailyworkDetailsDao;
	}

	@Autowired
	public void setDailyworkDetailsDao(DailyworkDetailsDaoI dailyworkDetailsDao) {
		this.dailyworkDetailsDao = dailyworkDetailsDao;
	}

	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	private OrderCategoryDaoI orderCategoryDao;

	public OrderCategoryDaoI getOrderCategoryDao() {
		return orderCategoryDao;
	}

	@Autowired
	public void setOrderCategoryDao(OrderCategoryDaoI orderCategoryDao) {
		this.orderCategoryDao = orderCategoryDao;
	}

	@Override
	public DataGrid<DailyworkDetails> getDetailsDg(DailyworkDetails dailyworkDetails) {
		DataGrid<DailyworkDetails> dg = new DataGrid<DailyworkDetails>();

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TDailyworkDetails t where t.dailydate=:dailydate";
		if (dailyworkDetails.getDailydate() != null) {
			params.put("dailydate", dailyworkDetails.getDailydate());
		} else {
			params.put("dailydate", new Date());
		}

		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(dailyworkDetailsDao.count(totalHql, params));

		List<TDailyworkDetails> tList = dailyworkDetailsDao.find(hql, params, dailyworkDetails.getPage(),
				dailyworkDetails.getRows());
		List<DailyworkDetails> dList = new ArrayList<DailyworkDetails>();
		if (tList != null && tList.size() > 0) {
			for (TDailyworkDetails t : tList) {
				DailyworkDetails d = new DailyworkDetails();
				BeanUtils.copyProperties(t, d);
				d.setTimepoint(t.getTDailywork().getTimepoint());
				d.setContent(t.getTDailywork().getContent());
				if (t.getTAccount() != null && t.getTAccount().getUsername() != null) {
					d.setRecorder(t.getTAccount().getUsername());
				}
				dList.add(d);
			}
		} else {
			// 如果没有当日记录则自动生成
			Date currentDate = new Date();
			int result = 0;
			if (dailyworkDetails.getDailydate() != null) {
				result = currentDate.compareTo(dailyworkDetails.getDailydate());
				currentDate = dailyworkDetails.getDailydate();
			}
			// log.info(result);
			if (result >= 0) { // 如果小于或等于今日
				String newSql = "from TDailywork t where 1=1";
				List<TDailywork> tdList = dailyworkDao.find(newSql);
				if (tdList != null && tdList.size() > 0) {
					for (TDailywork t : tdList) {
						TDailyworkDetails tDetails = new TDailyworkDetails();
						tDetails.setId(UUID.randomUUID().toString());
						tDetails.setTDailywork(t);
						tDetails.setDailydate(currentDate);
						tDetails.setStatus("未处理");
						dailyworkDetailsDao.save(tDetails);
						DailyworkDetails d = new DailyworkDetails();
						BeanUtils.copyProperties(tDetails, d);
						d.setTimepoint(t.getTimepoint());
						d.setContent(t.getContent());
						dList.add(d);
					}
				}
			}
		}
		dg.setRows(dList);

		return dg;
	}

	@Override
	public DataGrid<Dailywork> getDatagrid(Dailywork dailywork) {
		DataGrid<Dailywork> dg = new DataGrid<Dailywork>();

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TDailywork t where 1=1";

		if (dailywork.getContent() != null && !dailywork.getContent().trim().equals("")) {
			hql += " and t.content like :content";
			params.put("content", "%%" + dailywork.getContent().trim() + "%%");
		}

		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(dailyworkDao.count(totalHql, params));

		// 排序条件
		if (dailywork.getSort() != null && dailywork.getOrder() != null) {
			hql += " order by " + dailywork.getSort() + " " + dailywork.getOrder();
		}

		List<TDailywork> tList = dailyworkDao.find(hql, params, dailywork.getPage(), dailywork.getRows());
		List<Dailywork> dList = new ArrayList<Dailywork>();
		if (tList != null && tList.size() > 0) {
			for (TDailywork t : tList) {
				Dailywork d = new Dailywork();
				BeanUtils.copyProperties(t, d);
				d.setPublisher(t.getTAccount().getUsername());
				if (t.getTOrderCategory() != null && t.getTOrderCategory().getName() != null) {
					d.setCategory(t.getTOrderCategory().getName());
				} else {
					d.setCategory("");
				}
				dList.add(d);
			}
		}
		dg.setRows(dList);

		return dg;
	}

	@Override
	public void save(Dailywork dailywork) {
		log.info("==============" + dailywork.getPublisher() + " 发布日控管工作=================");
		if (dailywork.getReleasetime() == null) {
			dailywork.setReleasetime(new Date());
		}
		String[] tPoints = dailywork.getTimepoint().split("\\|");
		for (String tPoint : tPoints) {
			TDailywork t = new TDailywork();
			BeanUtils.copyProperties(dailywork, t, new String[] { "id", "timepoint" });
			// log.info(t.getReleasetime().toString());
			// 设置ID
			t.setId(UUID.randomUUID().toString());
			// 根据用户名获取发布人对象
			String hql = "from TAccount t where t.username = '" + dailywork.getPublisher() + "'";
			TAccount publisher = userDao.get(hql);
			t.setTAccount(publisher);
			// 根据ID获取专业类型
			if (dailywork.getCategory() != null && !dailywork.getCategory().trim().equals("")) {
				TOrderCategory category = orderCategoryDao.getForId(TOrderCategory.class, dailywork.getCategory());
				t.setTOrderCategory(category);
			}
			t.setTimepoint(tPoint);
			dailyworkDao.save(t);

			// 根据新添加记录生成当日Details记录
			TDailyworkDetails tDetails = new TDailyworkDetails();
			tDetails.setId(UUID.randomUUID().toString());
			tDetails.setTDailywork(t);
			tDetails.setDailydate(new Date());
			tDetails.setStatus("未处理");
			dailyworkDetailsDao.save(tDetails);
		}
	}

	@Override
	public void delete(String ids) {
		String[] id = ids.split(",");
		for (String s : id) {
			TDailywork t = dailyworkDao.getForId(TDailywork.class, s);
			dailyworkDao.delete(t);
		}
	}

	@Override
	public void edit(Dailywork dailywork) {
		TDailywork t = dailyworkDao.getForId(TDailywork.class, dailywork.getId());
		t.setContent(dailywork.getContent());
		// 根据用户名获取发布人对象
		String hql = "from TAccount t where t.username = '" + dailywork.getPublisher() + "'";
		TAccount publisher = userDao.get(hql);
		t.setTAccount(publisher);
		// 根据ID获取专业类型
		if (dailywork.getCategory() != null && !dailywork.getCategory().trim().equals("")) {
			TOrderCategory category = orderCategoryDao.getForId(TOrderCategory.class, dailywork.getCategory());
			t.setTOrderCategory(category);
		}
		if (dailywork.getReleasetime() == null) {
			t.setReleasetime(new Date());
		}

	}

	@Override
	public void editDetails(DailyworkDetails dailyworkDetails, SessionInfo sessionInfo) {
		TDailyworkDetails t = dailyworkDetailsDao.getForId(TDailyworkDetails.class, dailyworkDetails.getId());
		t.setStatus(dailyworkDetails.getStatus());
		t.setRecordtime(new Date());
		if (dailyworkDetails.getRemark() != null && !dailyworkDetails.getRemark().trim().equals("")) {
			t.setRemark(dailyworkDetails.getRemark());
		}
		t.setTAccount(userDao.getForId(TAccount.class, sessionInfo.getUser().getId()));
	}

	@Override
	public String getUntreated() {
		return "Test......";
	}

}
