package service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AttachmentDaoI;
import dao.GuaranteeDaoI;
import dao.GuaranteeNoticeDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TAttachment;
import model.TGuarantee;
import model.TGuaranteeNotice;
import pageModel.DataGrid;
import pageModel.Guarantee;
import pageModel.GuaranteeNotice;
import service.GuaranteeServiceI;

/**
 * 保障模板Service实现类
 * 
 * @author leoSong
 * @date 2017/10/19
 */
@Service(value = "guaranteeService")
public class GuaranteeServiceImpl implements GuaranteeServiceI {
	private static final Logger logger = Logger.getLogger(GuaranteeServiceImpl.class);

	private GuaranteeDaoI guaranteeDao;
	private GuaranteeNoticeDaoI guaranteeNoticeDao;
	private UserDaoI userDao;
	private AttachmentDaoI attachmentDao;

	public AttachmentDaoI getAttachmentDao() {
		return attachmentDao;
	}

	@Autowired
	public void setAttachmentDao(AttachmentDaoI attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public GuaranteeDaoI getGuaranteeDao() {
		return guaranteeDao;
	}

	@Autowired
	public void setGuaranteeDao(GuaranteeDaoI guaranteeDao) {
		this.guaranteeDao = guaranteeDao;
	}

	public GuaranteeNoticeDaoI getGuaranteeNoticeDao() {
		return guaranteeNoticeDao;
	}

	@Autowired
	public void setGuaranteeNoticeDao(GuaranteeNoticeDaoI guaranteeNoticeDao) {
		this.guaranteeNoticeDao = guaranteeNoticeDao;
	}

	@Override
	public DataGrid<Guarantee> getGuaranteeDg(Guarantee guarantee) {
		DataGrid<Guarantee> dg = new DataGrid<Guarantee>();
		Map<String, Object> params = new HashMap<String, Object>(16);
		String hql = "from TGuarantee t where 1=1";

		// 查询条件
		if (guarantee.getTitle() != null && !guarantee.getTitle().trim().equals("")) {
			hql += " and t.title like :title";
			params.put("title", "%%" + guarantee.getTitle().trim() + "%%");
		}
		if (guarantee.getRecorder() != null && !guarantee.getRecorder().trim().equals("")) {
			hql += " and t.recorder=:recorder";
			params.put("recorder", guarantee.getRecorder());
		}
		if (guarantee.getKeywords() != null && !guarantee.getKeywords().trim().equals("")) {
			hql += " and (t.content like :content or t.requirement like :requirement or t.noticetemplate like :noticetemplate)";
			params.put("content", "%%" + guarantee.getKeywords().trim() + "%%");
			params.put("requirement", "%%" + guarantee.getKeywords().trim() + "%%");
			params.put("noticetemplate", "%%" + guarantee.getKeywords().trim() + "%%");
		}
		if (guarantee.getStatus() != null && !guarantee.getStatus().trim().equals("")) {
			hql += " and t.status=:status";
			params.put("status", guarantee.getStatus());
		}
		if (guarantee.getTimestart() != null) {
			hql += " and t.timestart>=:timestart";
			params.put("timestart", guarantee.getTimestart());
		}
		if (guarantee.getTimeend() != null) {
			hql += " and t.timeend<=:timeend";
			params.put("timeend", guarantee.getTimeend());
		}

		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(guaranteeDao.count(totalHql, params));

		// 排序条件
		if (guarantee.getSort() != null && guarantee.getOrder() != null) {
			hql += " order by " + guarantee.getSort() + " " + guarantee.getOrder();
		}

		// 根据HQL获取记录
		List<TGuarantee> tList = guaranteeDao.find(hql, params, guarantee.getPage(), guarantee.getRows());
		List<Guarantee> gList = new ArrayList<Guarantee>();
		if (tList != null && tList.size() > 0) {
			for (TGuarantee t : tList) {
				Guarantee g = new Guarantee();
				BeanUtils.copyProperties(t, g);
				g.setRecorder(userDao.getForId(TAccount.class, t.getRecorder()).getUsername());
				gList.add(g);
			}
		}
		dg.setRows(gList);

		return dg;
	}

	@Override
	public List<Guarantee> getDataList(Guarantee guarantee) {
		String hql = "from TGuarantee t where t.status='保障中'";
		if (guarantee.getContent() != null && !guarantee.getContent().trim().equals("")) {
			hql += " and t.content like '%" + guarantee.getContent() + "%'";
		}

		List<Guarantee> gList = new ArrayList<Guarantee>();
		List<TGuarantee> tList = guaranteeDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TGuarantee t : tList) {
				Guarantee g = new Guarantee();
				BeanUtils.copyProperties(t, g);
				g.setRecorder(userDao.getForId(TAccount.class, t.getRecorder()).getUsername());
				gList.add(g);
			}
		} else {
			Guarantee g = new Guarantee();
			g.setId("0");
			g.setTitle("近期暂无保障");
			gList.add(g);
		}
		return gList;
	}

	@Override
	public Guarantee getGuarantee(String id) {
		Guarantee g = new Guarantee();
		TGuarantee t = guaranteeDao.getForId(TGuarantee.class, id);
		BeanUtils.copyProperties(t, g);
		g.setRecorder(userDao.getForId(TAccount.class, t.getRecorder()).getUsername());

		// 查询该记录是否有关联的附件
		String hql = "from TAttachment t where t.relatedid='" + id + "'";
		List<TAttachment> tList = attachmentDao.find(hql);
		if (tList != null && tList.size() > 0) {
			String tempStr = "";
			// 获取服务器路径
			String path = ServletActionContext.getRequest().getContextPath();
			// logger.info(path);
			for (TAttachment tAtt : tList) {
				tempStr += "<a href='" + path + tAtt.getUrl() + "' target='_blank'>" + tAtt.getFilename()
						+ "</a>&nbsp;&nbsp;";
			}
			g.setAttachment(tempStr);
		} else {
			g.setAttachment("");
		}
		return g;
	}

	@Override
	public Guarantee save(Guarantee guarantee) {
		TGuarantee t = new TGuarantee();
		BeanUtils.copyProperties(guarantee, t, new String[] { "recorder" });
		String hql = "from TAccount t where t.username = '" + guarantee.getRecorder() + "'";
		TAccount user = userDao.get(hql);
		t.setRecorder(user.getId());
		guaranteeDao.save(t);

		// 根据保障开始时间timestart、保障结束时间timeend和通报时间点timepoint动态生成TGuaranteeNotice记录
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 计算天数
		int days = daysBetween(guarantee.getTimestart(), guarantee.getTimeend());
		// 获取时间点
		String[] timePoints = guarantee.getTimepoint().split("\\|");
		// 循环生成记录
		String keyTimePoint = "00:00";
		if (days >= 0 && timePoints.length > 0) {
			if (timePoints.length == 1 && keyTimePoint.equals(timePoints[0])) {
				t.setTimepoint("无");
				guaranteeDao.save(t);
				guarantee.setTimepoint("无");
				return guarantee;
			}
			Calendar c = Calendar.getInstance();
			for (int i = 0; i < days + 1; ++i) {
				c.setTime(guarantee.getTimestart());
				c.add(Calendar.DAY_OF_MONTH, i);
				for (String str : timePoints) {
					// logger.info(sdf.format(c.getTime()) + "---" + str);
					TGuaranteeNotice tGN = new TGuaranteeNotice();
					tGN.setId(UUID.randomUUID().toString());
					tGN.setNoticedate(c.getTime());
					tGN.setNoticetime(str);
					tGN.setTGuarantee(t);
					guaranteeNoticeDao.save(tGN);
				}
			}
		}

		return guarantee;
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TGuarantee t where t.id='" + id + "'";
		guaranteeDao.executeHql(hql);
	}

	@Override
	public Guarantee edit(Guarantee guarantee) {
		TGuarantee t = guaranteeDao.getForId(TGuarantee.class, guarantee.getId());
		BeanUtils.copyProperties(guarantee, t, new String[] { "recorder" });
		String hql = "from TAccount t where t.username = '" + guarantee.getRecorder() + "'";
		TAccount user = userDao.get(hql);
		t.setRecorder(user.getId());
		return guarantee;
	}

	@Override
	public void updateStatus(String id) {
		TGuarantee t = guaranteeDao.getForId(TGuarantee.class, id);
		t.setStatus("已结束");
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 *            开始时间
	 * @param date2
	 *            结束时间
	 * @return 返回相差的天数
	 */
	public int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(betweenDays));
	}

	@Override
	public DataGrid<GuaranteeNotice> getNoticeByRelated(GuaranteeNotice guaranteeNotice) {
		DataGrid<GuaranteeNotice> dg = new DataGrid<GuaranteeNotice>();
		Map<String, Object> params = new HashMap<String, Object>(16);
		String hql = "from TGuaranteeNotice t where 1=1";

		if (guaranteeNotice.getRelated() != null && !guaranteeNotice.getRelated().trim().equals("")) {
			hql += " and t.TGuarantee.id=:related";
			params.put("related", guaranteeNotice.getRelated());
		}

		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(guaranteeNoticeDao.count(totalHql, params));

		// 排序条件
		if (guaranteeNotice.getSort() != null && guaranteeNotice.getOrder() != null) {
			hql += " order by " + guaranteeNotice.getSort() + " " + guaranteeNotice.getOrder();
		}

		List<TGuaranteeNotice> tList = guaranteeNoticeDao.find(hql, params, guaranteeNotice.getPage(),
				guaranteeNotice.getRows());
		List<GuaranteeNotice> gList = new ArrayList<GuaranteeNotice>();
		if (tList != null && tList.size() > 0) {
			for (TGuaranteeNotice t : tList) {
				GuaranteeNotice g = new GuaranteeNotice();
				BeanUtils.copyProperties(t, g);
				g.setRelated(t.getTGuarantee().getId());
				if (t.getRecorder() != null && !t.getRecorder().trim().equals("")) {
					TAccount user = userDao.getForId(TAccount.class, t.getRecorder());
					g.setRecorder(user.getUsername());
				}
				gList.add(g);
			}
		}
		dg.setRows(gList);

		return dg;
	}

	@Override
	public void saveNotice(GuaranteeNotice guaranteeNotice) {
		TGuaranteeNotice t = new TGuaranteeNotice();
		BeanUtils.copyProperties(guaranteeNotice, t);
		t.setId(UUID.randomUUID().toString());
		TGuarantee tg = guaranteeDao.getForId(TGuarantee.class, guaranteeNotice.getRelated());
		t.setTGuarantee(tg);
		String hql = "from TAccount t where t.username = '" + guaranteeNotice.getRecorder() + "'";
		TAccount user = userDao.get(hql);
		t.setRecorder(user.getId());
		guaranteeNoticeDao.save(t);
	}

	@Override
	public void delNotice(String id) {
		String hql = "delete from TGuaranteeNotice t where t.id='" + id + "'";
		guaranteeNoticeDao.executeHql(hql);
	}

	@Override
	public GuaranteeNotice editNotice(GuaranteeNotice guaranteeNotice) {
		TGuaranteeNotice t = guaranteeNoticeDao.getForId(TGuaranteeNotice.class, guaranteeNotice.getId());
		BeanUtils.copyProperties(guaranteeNotice, t, new String[] { "id", "recorder" });
		String hql = "from TAccount t where t.username = '" + guaranteeNotice.getRecorder() + "'";
		TAccount user = userDao.get(hql);
		t.setRecorder(user.getId());
		return guaranteeNotice;
	}

	@Override
	public List<GuaranteeNotice> getDataForExcel(GuaranteeNotice guaranteeNotice) {
		List<GuaranteeNotice> gList = new ArrayList<GuaranteeNotice>();
		logger.info(guaranteeNotice.getRelated());
		String hql = "from TGuaranteeNotice t where t.TGuarantee.id='" + guaranteeNotice.getRelated() + "'";
		List<TGuaranteeNotice> tList = guaranteeNoticeDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TGuaranteeNotice t : tList) {
				GuaranteeNotice g = new GuaranteeNotice();
				BeanUtils.copyProperties(t, g, new String[] { "recorder" });
				if (t.getRecorder() != null && !t.getRecorder().trim().equals("")) {
					TAccount user = userDao.getForId(TAccount.class, t.getRecorder());
					g.setRecorder(user.getUsername());
				}
				gList.add(g);
			}
		}
		return gList;
	}

}
