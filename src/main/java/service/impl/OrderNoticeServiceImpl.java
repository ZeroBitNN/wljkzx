package service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.OrderNoticeDaoI;
import dao.UserDaoI;
import model.SessionInfo;
import model.TAccount;
import model.TOrder;
import model.TOrderNotice;
import pageModel.DataGrid;
import pageModel.OrderNotice;
import pageModel.TbReport;
import service.OrderNoticeServiceI;

@Service(value = "orderNoticeService")
public class OrderNoticeServiceImpl implements OrderNoticeServiceI {
	private static final Logger logger = Logger.getLogger(OrderNoticeServiceImpl.class);
	private OrderNoticeDaoI orderNoticeDao;
	private UserDaoI userDao;

	public OrderNoticeDaoI getOrderNoticeDao() {
		return orderNoticeDao;
	}

	@Autowired
	public void setOrderNoticeDao(OrderNoticeDaoI orderNoticeDao) {
		this.orderNoticeDao = orderNoticeDao;
	}
	
	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Override
	public DataGrid<OrderNotice> getAll(OrderNotice orderNotice) {
		DataGrid<OrderNotice> dg = new DataGrid<OrderNotice>();

		// 配置HQL查询语句
		String hql = "from TOrderNotice t where t.TOrder.id='" + orderNotice.getOrderid() + "'";
		String totalHql = "select count(*) " + hql;
		if (orderNotice.getSort() != null) {
			hql += " order by " + orderNotice.getSort() + " " + orderNotice.getOrder();
		}

		// 将查询出的数据转换为PageModel并放入DataGrid中
		List<TOrderNotice> list = orderNoticeDao.find(hql, orderNotice.getPage(), orderNotice.getRows());
		List<OrderNotice> oList = new ArrayList<OrderNotice>();
		if (list != null && list.size() > 0) {
			for (TOrderNotice t : list) {
				OrderNotice o = new OrderNotice();
				o.setId(t.getId());
				o.setAuthor(t.getTAccount().getUsername());
				o.setTime(t.getTime());
				o.setContent(t.getContent());
				o.setOrderid(t.getTOrder().getId());
				oList.add(o);
			}
		}
		dg.setRows(oList);

		// 查询结果记录总数
		dg.setTotal(orderNoticeDao.count(totalHql));
		return dg;
	}

	@Override
	public OrderNotice add(OrderNotice orderNotice,SessionInfo sessionInfo) {
		orderNotice.setId(UUID.randomUUID().toString());
		orderNotice.setTime(new Date());
		orderNotice.setAuthor(sessionInfo.getUser().getUsername());
		
		TOrderNotice t = new TOrderNotice();
		t.setId(orderNotice.getId());
		TAccount account = new TAccount();
		account.setId(sessionInfo.getUser().getId());
		t.setTAccount(account);
		t.setTOrder(new TOrder(orderNotice.getOrderid()));
		t.setContent(orderNotice.getContent());
		t.setTime(orderNotice.getTime());
		orderNoticeDao.save(t);
		
		return orderNotice;
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TOrderNotice t where t.id='" + id + "'";
		orderNoticeDao.executeHql(hql);
	}

	@Override
	public DataGrid<TbReport> getReportDg(TbReport tbReport) {
		DataGrid<TbReport> dg = new DataGrid<TbReport>();
		Long sum = 0L;
		
		List<TbReport> rList = new ArrayList<TbReport>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TAccount t where t.username<>'admin'";
		List<TAccount> tList = userDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TAccount t : tList) {
				TbReport r = new TbReport();
				r.setName(t.getUsername());
				
				hql = "select count(*) from TOrderNotice t where t.TAccount.id=:id and t.time>=:createtimestart and t.time<=:createtimeend";
				params.put("id", t.getId());
				if (tbReport.getCreatetimeStart() == null) {
					params.put("createtimestart", getFirstDay());
				} else {
					params.put("createtimestart", tbReport.getCreatetimeStart());
				}
				if (tbReport.getCreatetimeEnd() == null){
					params.put("createtimeend", new Date());
				} else {
					params.put("createtimeend", tbReport.getCreatetimeEnd());
				}
				r.setTbNumber(orderNoticeDao.count(hql, params));
				
				sum += r.getTbNumber();
				rList.add(r);
				
			}
		}
		dg.setTotal((long)tList.size());
		dg.setRows(rList);
		
		// Footer
		List<TbReport> rFooterList = new ArrayList<TbReport>();
		TbReport rFooter = new TbReport();
		rFooter.setName("合计：");
		rFooter.setTbNumber(sum);
		rFooterList.add(rFooter);
		dg.setFooter(rFooterList);
		
		return dg;
	}
	
	private Date getFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

}
