package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import dao.PerfNumDaoI;
import dao.PerfParamDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TPerfNum;
import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.PerfNum;
import service.PerfNumServiceI;
import util.StringUtil;

@Service(value = "perfNumService")
public class PerfNumServiceImpl implements PerfNumServiceI {
	private static final Logger logger = Logger.getLogger(PerfNumServiceImpl.class);

	private PerfNumDaoI perfNumDao;

	public PerfNumDaoI getPerfNumDao() {
		return perfNumDao;
	}

	@Autowired
	public void setPerfNumDao(PerfNumDaoI perfNumDao) {
		this.perfNumDao = perfNumDao;
	}

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

	@Override
	public DataGrid<PerfNum> getDatagrid(PerfNum perfNum) {
		DataGrid<PerfNum> dg = new DataGrid<PerfNum>();
		String hql = "from TPerfNum t where 1=1";
		if (perfNum.getPerfdate() != null && !perfNum.getPerfdate().equals("")) {
			hql += " and t.perfdate='" + perfNum.getPerfdate() + "'";
		} else {
			// 读取当前月份数据
			String perfdate = StringUtil.dateToYMString(new Date());
			logger.info("读取当前月份 (" + perfdate + ") 的数据");
			hql += " and t.perfdate='" + perfdate + "'";
		}

		String totalHql = "select count(*) " + hql;
		dg.setTotal(perfNumDao.count(totalHql));

		List<TPerfNum> tList = perfNumDao.find(hql);
		List<PerfNum> pList = new ArrayList<PerfNum>();
		if (tList != null && tList.size() > 0) {
			// 如果有数据则读取
			for (TPerfNum t : tList) {
				PerfNum p = new PerfNum();
				BeanUtils.copyProperties(t, p);
				p.setName(t.getTAccount().getUsername());
				p.setItem(t.getTPerfParam().getName());
				pList.add(p);
			}
		} else if (tList.size() == 0) {
			// 如果没有数据则初始化当月数据
			Long sumCreate = 0L;
			String userHql = "from TAccount t where t.username<>'admin'";
			List<TAccount> userList = userDao.find(userHql);
			if (userList != null && userList.size() > 0) {
				for (TAccount user : userList) {
					String itemHql = "from TPerfParam t where type='类目' and pid is not null";
					List<TPerfParam> itemList = perfParamDao.find(itemHql);
					if (itemList != null && itemList.size() > 0) {
						for (TPerfParam item : itemList) {
							TPerfNum tp = new TPerfNum();
							tp.setId(UUID.randomUUID().toString());
							tp.setTAccount(user);
							tp.setTPerfParam(item);
							tp.setPerfdate(StringUtil.dateToYMString(new Date()));
							tp.setItemgroup(item.getTPerfParam().getName());
							perfNumDao.save(tp);

							PerfNum p = new PerfNum();
							BeanUtils.copyProperties(tp, p);
							p.setName(user.getUsername());
							p.setItem(item.getName());
							pList.add(p);
							sumCreate++;
						}
					}
				}
			}
			dg.setTotal(sumCreate);
		}

		dg.setRows(pList);

		return dg;
	}

	@Override
	public void saveNum(PerfNum perfNum) {
		// 录入的数据
		if (perfNum.getChangeRows() != null && !perfNum.getChangeRows().equals("")) {
			List<PerfNum> itemList = JSON.parseArray(perfNum.getChangeRows(), PerfNum.class);
			if (itemList != null && itemList.size() > 0) {
				for (PerfNum p : itemList) {
					if (p != null) {
						TPerfNum tp = perfNumDao.getForId(TPerfNum.class, p.getId());
						if (p.getPercent() != null) {
							tp.setPercent(p.getPercent());
						}
						if (p.getValue() != null) {
							tp.setValue(p.getValue());
						}
					}
				}
			}
		}

	}

}
