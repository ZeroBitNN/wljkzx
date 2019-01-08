package service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDaoI;
import dao.WorkrecordDaoI;
import model.TAccount;
import model.TWorkrecord;
import pageModel.DataGrid;
import pageModel.RecReport;
import pageModel.Workrecord;
import service.WorkrecordServiceI;

@Service(value = "workrecordService")
public class WorkrecordServiceImpl implements WorkrecordServiceI {
	private static final Logger logger = Logger.getLogger(WorkrecordServiceImpl.class);
	private WorkrecordDaoI workrecordDao;
	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public WorkrecordDaoI getWorkrecordDao() {
		return workrecordDao;
	}

	@Autowired
	public void setWorkrecordDao(WorkrecordDaoI workrecordDao) {
		this.workrecordDao = workrecordDao;
	}

	@Override
	public DataGrid<Workrecord> getDatagrid(Workrecord workrecord) {
		DataGrid<Workrecord> dg = new DataGrid<Workrecord>();

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TWorkrecord t where 1=1";

		// 查询条件
		if (workrecord.getProposer() != null && !workrecord.getProposer().trim().equals("")) {
			hql += " and t.proposer like :proposer";
			params.put("proposer", "%%" + workrecord.getProposer().trim() + "%%");
		}
		if (workrecord.getStatus() != null && !workrecord.getStatus().trim().equals("")) {
			hql += " and t.status=:status";
			params.put("status", workrecord.getStatus());
		}
		if (workrecord.getFaultnumber() != null && !workrecord.getFaultnumber().trim().equals("")) {
			hql += " and t.faultnumber like :faultnumber";
			params.put("faultnumber", "%%" + workrecord.getFaultnumber().trim() + "%%");
		}
		if (workrecord.getHandler() != null && !workrecord.getHandler().trim().equals("")) {
			hql += " and t.TAccountByHandler.id=:handler";
			params.put("handler", workrecord.getHandler());
		}
		if (workrecord.getDescribe() != null && !workrecord.getDescribe().trim().equals("")) {
			hql += " and t.describe like :describe";
			params.put("describe", "%%" + workrecord.getDescribe().trim() + "%%");
		}
		if (workrecord.getSituation() != null && !workrecord.getSituation().trim().equals("")) {
			hql += " and t.situation like :situation";
			params.put("situation", "%%" + workrecord.getSituation().trim() + "%%");
		}
		if (workrecord.getCreatetimeStart() != null) {
			hql += " and t.createtime>=:createtimestart";
			params.put("createtimestart", workrecord.getCreatetimeStart());
		}
		if (workrecord.getCreatetimeEnd() != null) {
			hql += " and t.createtime<=:createtimeend";
			params.put("createtimeend", workrecord.getCreatetimeEnd());
		}
		if (workrecord.getModifytimeStart() != null) {
			hql += " and t.modifytime>=:modifytimestart";
			params.put("modifytimestart", workrecord.getModifytimeStart());
		}
		if (workrecord.getModifytimeEnd() != null) {
			hql += " and t.modifytime<=:modifytimeend";
			params.put("modifytimeend", workrecord.getModifytimeEnd());
		}
		if (workrecord.getRecordtype()!=null && !workrecord.getRecordtype().equals("")){
			hql += " and t.recordtype like :recordtype";
			params.put("recordtype", "%%" + workrecord.getRecordtype().trim() + "%%");
		}
		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(workrecordDao.count(totalHql, params));

		// 排序条件
		if (workrecord.getSort() != null && workrecord.getOrder() != null) {
			hql += " order by " + workrecord.getSort() + " " + workrecord.getOrder();
		}

		// 根据HQL获取记录
		List<TWorkrecord> tList = workrecordDao.find(hql, params, workrecord.getPage(), workrecord.getRows());
		List<Workrecord> wList = new ArrayList<Workrecord>();
		if (tList != null && tList.size() > 0) {
			for (TWorkrecord t : tList) {
				Workrecord w = new Workrecord();
				BeanUtils.copyProperties(t, w);
				w.setInputer(t.getTAccountByInputer().getUsername());
				w.setHandler(t.getTAccountByHandler().getUsername());
				wList.add(w);
			}
		}
		dg.setRows(wList);

		return dg;
	}

	@Override
	public void save(Workrecord workrecord) {
		TWorkrecord t = new TWorkrecord();
		BeanUtils.copyProperties(workrecord, t);
		// logger.info(workrecord.getHandler());//[service.impl.WorkrecordServiceImpl]admin
		TAccount tAccInputer = getUserbyName(workrecord.getInputer());
		TAccount tAccHandler = getUserbyName(workrecord.getHandler());
		t.setTAccountByInputer(tAccInputer);
		t.setTAccountByHandler(tAccHandler);
		t.setCreatetime(new Date());

		workrecordDao.save(t);
	}

	@Override
	public void edit(Workrecord workrecord) {
		TWorkrecord t = workrecordDao.getForId(TWorkrecord.class, workrecord.getId());
		BeanUtils.copyProperties(workrecord, t, new String[] { "id" });
		if (workrecord.getModifytime() == null) {
			t.setModifytime(new Date());
		}

	}

	private TAccount getUserbyName(String username) {
		String hql = "from TAccount t where t.username='" + username + "'";
		TAccount t = userDao.get(hql);
		return t;
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TWorkrecord t where t.id='" + id + "'";
		workrecordDao.executeHql(hql);
	}

	@Override
	public List<RecReport> getReport(RecReport recReport) {
		List<RecReport> rList = new ArrayList<RecReport>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TAccount t where t.username<>'admin'";
		List<TAccount> tList = userDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TAccount t : tList) {
				RecReport r = new RecReport();
				// 设置用户
				r.setName(t.getUsername());

				hql = "select count(*) from TWorkrecord t where t.status=:status and t.TAccountByHandler.id=:id and t.createtime>=:createtimestart and t.createtime<=:createtimeend";
				// 获得用户的未处理工单数量
				params.put("status", "未处理");
				params.put("id", t.getId());
				if (recReport.getCreatetimeStart() == null) {
					// 获取当前月的第一天
					//logger.info(getFirstDay().toString());
					params.put("createtimestart", getFirstDay());
				} else {
					params.put("createtimestart", recReport.getCreatetimeStart());
				}
				if (recReport.getCreatetimeEnd() == null) {
					params.put("createtimeend", new Date());
				} else {
					params.put("createtimeend", recReport.getCreatetimeEnd());
				}
				r.setUntreated(workrecordDao.count(hql, params));

				// 获取得用户的已处理工单数量
				params.put("status", "已处理");
				r.setProcessed(workrecordDao.count(hql, params));

				// 统计所有工单数量
				r.setSum(r.getUntreated() + r.getProcessed());
				rList.add(r);
			}
		}

		return rList;
	}

	private Date getFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	@Override
	public DataGrid<RecReport> getReportDg(RecReport recReport) {
		DataGrid<RecReport> dg = new DataGrid<RecReport>();
		Long untreatedSum = 0L;
		Long processedSum = 0L;
		Long sumSum = 0L;

		List<RecReport> rList = new ArrayList<RecReport>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TAccount t where t.username<>'admin'";
		List<TAccount> tList = userDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TAccount t : tList) {
				RecReport r = new RecReport();
				// 设置用户
				r.setName(t.getUsername());

				hql = "select count(*) from TWorkrecord t where t.status=:status and t.TAccountByHandler.id=:id and t.createtime>=:createtimestart and t.createtime<=:createtimeend";
				// 获得用户的未处理工单数量
				params.put("status", "未处理");
				params.put("id", t.getId());
				if (recReport.getCreatetimeStart() == null) {
					params.put("createtimestart", getFirstDay());
				} else {
					params.put("createtimestart", recReport.getCreatetimeStart());
				}
				if (recReport.getCreatetimeEnd() == null) {
					params.put("createtimeend", new Date());
				} else {
					params.put("createtimeend", recReport.getCreatetimeEnd());
				}
				r.setUntreated(workrecordDao.count(hql, params));
				untreatedSum += r.getUntreated();

				// 获取得用户的已处理工单数量
				params.put("status", "已处理");
				r.setProcessed(workrecordDao.count(hql, params));
				processedSum += r.getProcessed();

				// 统计所有工单数量
				r.setSum(r.getUntreated() + r.getProcessed());
				sumSum += r.getSum();

				rList.add(r);
			}
		}

		dg.setTotal((long) tList.size());
		dg.setRows(rList);

		// Footer
		List<RecReport> rFooterList = new ArrayList<RecReport>();
		RecReport rFooter = new RecReport();
		rFooter.setName("合计：");
		rFooter.setUntreated(untreatedSum);
		rFooter.setProcessed(processedSum);
		rFooter.setSum(sumSum);
		rFooterList.add(rFooter);
		dg.setFooter(rFooterList);

		return dg;
	}

	@Override
	public DataGrid<Workrecord> getUntreated(Workrecord workrecord, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		DataGrid<Workrecord> dg = new DataGrid<Workrecord>();
		String hql = "from TWorkrecord t where t.TAccountByHandler.username = :username and t.status = '未处理'";
		params.put("username", username);

		String totalHql = "select count(*) " + hql;
		dg.setTotal(workrecordDao.count(totalHql, params));

		if (workrecord.getSort() != null && workrecord.getOrder() != null) {
			hql += " order by " + workrecord.getSort() + " " + workrecord.getOrder();
		}

		List<TWorkrecord> tList = workrecordDao.find(hql, params);
		List<Workrecord> wList = new ArrayList<Workrecord>();
		if (tList != null && tList.size() > 0) {
			for (TWorkrecord t : tList) {
				Workrecord w = new Workrecord();
				BeanUtils.copyProperties(t, w);
				w.setInputer(t.getTAccountByInputer().getUsername());
				w.setHandler(t.getTAccountByHandler().getUsername());
				wList.add(w);
			}
		}
		dg.setRows(wList);

		return dg;
	}

	@Override
	public void updateHandler(Workrecord workrecord) {
		TWorkrecord t = workrecordDao.getForId(TWorkrecord.class, workrecord.getId());

		String oldHandler = t.getTAccountByHandler().getUsername();
		String newHandler = workrecord.getHandler();
		logger.info("转派记录：" + oldHandler + " --->> " + newHandler);

		TAccount tAccHandler = getUserbyName(workrecord.getHandler());
		t.setTAccountByHandler(tAccHandler);
	}
}
