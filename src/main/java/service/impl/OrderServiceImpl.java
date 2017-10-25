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

import dao.OrderCategoryDaoI;
import dao.OrderDaoI;
import dao.OrderTypeDaoI;
import model.SessionInfo;
import model.TAccount;
import model.TOrder;
import model.TOrderCategory;
import model.TOrderType;
import pageModel.DataGrid;
import pageModel.Order;
import pageModel.OrderCategory;
import pageModel.OrderType;
import service.OrderServiceI;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderServiceI {
	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

	private OrderDaoI orderDao;

	public OrderDaoI getOrderDao() {
		return orderDao;
	}

	@Autowired
	public void setOrderDao(OrderDaoI orderDao) {
		this.orderDao = orderDao;
	}

	private OrderTypeDaoI orderTypeDao;

	public OrderTypeDaoI getOrderTypeDao() {
		return orderTypeDao;
	}

	@Autowired
	public void setOrderTypeDao(OrderTypeDaoI orderTypeDao) {
		this.orderTypeDao = orderTypeDao;
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
	public DataGrid<Order> getAllOrders(Order order) {
		if (order.getTypeid() != null && order.getTypeid().equals("请选择")) {
			order.setTypeid("");
		}
		if (order.getOrderCategory() != null && order.getOrderCategory().equals("请选择")) {
			order.setOrderCategory("");
		}
		DataGrid<Order> dg = new DataGrid<Order>();
		logger.info(order.getTypeid());
		if ((order.getTypeid() != null && !order.getTypeid().trim().equals(""))
				|| (order.getIsArchived() != null && !order.getIsArchived().trim().equals(""))
				|| (order.getOrderCategory() != null && !order.getOrderCategory().trim().equals(""))) {
			return getByCondition(order, dg);
		} else {
			return getAll(order, dg);
		}
	}

	private DataGrid<Order> getByCondition(Order order, DataGrid<Order> dg) {
		List<TOrder> list = new ArrayList<TOrder>();
		List<Order> orderList = new ArrayList<Order>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = null;
		String totalHql = null;

		if (order.getTypeid() != null && !order.getTypeid().trim().equals("")) {
			if (order.getIsArchived() != null && !order.getIsArchived().trim().equals("")) {
				if (order.getOrderCategory() != null && !order.getOrderCategory().trim().equals("")) {
					// 有1 2 3
					hql = "from TOrder t where t.TOrderType.id = :id and t.isArchived = :isArchived and t.TOrderCategory.id = :categoryId";
					params.put("id", order.getTypeid());
					params.put("isArchived", order.getIsArchived());
					params.put("categoryId", order.getOrderCategory());
					totalHql = "select count(*) " + hql;
				} else {
					// 有1 2
					hql = "from TOrder t where t.TOrderType.id = :id and t.isArchived = :isArchived";
					params.put("id", order.getTypeid());
					params.put("isArchived", order.getIsArchived());
					totalHql = "select count(*) " + hql;
				}
			} else {
				if (order.getOrderCategory() != null && !order.getOrderCategory().trim().equals("")) {
					// 有1 3
					hql = "from TOrder t where t.TOrderType.id = :id and t.TOrderCategory.id = :categoryId";
					params.put("id", order.getTypeid());
					params.put("categoryId", order.getOrderCategory());
					totalHql = "select count(*) " + hql;
				} else {
					// 有1
					hql = "from TOrder t where t.TOrderType.id = :id";
					params.put("id", order.getTypeid());
					totalHql = "select count(*) " + hql;
				}
			}
		} else {
			if (order.getIsArchived() != null && !order.getIsArchived().trim().equals("")) {
				if (order.getOrderCategory() != null && !order.getOrderCategory().trim().equals("")) {
					// 有2 3
					hql = "from TOrder t where t.isArchived = :isArchived and t.TOrderCategory.id = :categoryId";
					params.put("isArchived", order.getIsArchived());
					params.put("categoryId", order.getOrderCategory());
					totalHql = "select count(*) " + hql;
				} else {
					// 有2
					hql = "from TOrder t where t.isArchived = :isArchived";
					params.put("isArchived", order.getIsArchived());
					if (order.getSort().equals("isArchived")) {
						order.setSort("createtime");
					}
					totalHql = "select count(*) " + hql;
				}
			} else {
				// 有3
				hql = "from TOrder t where t.TOrderCategory.id = :id";
				params.put("id", order.getOrderCategory());
				totalHql = "select count(*) " + hql;
			}
		}
		// 排序
		if (order.getSort() != null) {
			hql += " order by " + order.getSort() + " " + order.getOrder();
		}

		list = orderDao.find(hql, params, order.getPage(), order.getRows());
		for (TOrder t : list) {
			Order o = new Order();
			o.setId(t.getId());
			o.setTypeid(t.getTOrderType().getName());
			if (t.getNoticetime() != null) {
				o.setNoticetime(t.getNoticetime());
			}
			o.setIsArchived(t.getIsArchived());
			if (t.getIsArchived() != null && t.getIsArchived().equals("true")) {
				o.setArchivedtime(t.getArchivedtime());
			}
			o.setOrdernum(t.getOrdernum());
			o.setOrderCategory(t.getTOrderCategory().getName());
			o.setTitle(t.getTitle());
			o.setAuthor(t.getTAccountByAuthorid().getUsername());
			o.setCreatetime(t.getCreatetime());
			if (t.getTAccountByModifyid()!=null){
				o.setEditor(t.getTAccountByModifyid().getUsername());
			}
			o.setModifytime(t.getModifytime());
			o.setContent(t.getContent());
			orderList.add(o);
		}
		dg.setRows(orderList);

		// 查询结果记录数

		dg.setTotal(orderDao.count(totalHql, params));
		logger.info("共找到" + dg.getTotal() + "条记录");

		return dg;
	}

	private DataGrid<Order> getAll(Order order, DataGrid<Order> dg) {
		List<TOrder> list = null;
		// 查询工单
		String hql = "from TOrder t";

		// 如果有运维单号
		if (order.getOrdernum() != null && !order.getOrdernum().trim().equals("")) {
			hql = "from TOrder t where t.ordernum like '%" + order.getOrdernum() + "%'";
		}
		// 如果有标题
		if (order.getTitle() != null && !order.getTitle().trim().equals("")) {
			hql = "from TOrder t where t.title like '%" + order.getTitle() + "%'";
		}
		// 如果两个条件都有
		if ((order.getTitle() != null && !order.getTitle().trim().equals(""))
				&& (order.getOrdernum() != null && !order.getOrdernum().trim().equals(""))) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ordernum", order.getOrdernum());
			params.put("title", order.getTitle());
			list = orderDao.find("from TOrder t where t.ordernum = :ordernum and t.title = :title", params,
					order.getPage(), order.getRows());
		} else {
			// 排序
			if (order.getSort() != null) {
				hql += " order by " + order.getSort() + " " + order.getOrder() + ",t.createtime desc";
			}
			list = orderDao.find(hql, order.getPage(), order.getRows());
		}

		List<Order> orderList = new ArrayList<Order>();
		if (list != null && list.size() > 0) {
			for (TOrder t : list) {
				Order o = new Order();
				o.setId(t.getId());
				o.setTypeid(t.getTOrderType().getName());
				if (t.getNoticetime() != null) {
					o.setNoticetime(t.getNoticetime());
				}
				o.setIsArchived(t.getIsArchived());
				if (t.getIsArchived().equals("已归档")) {
					o.setArchivedtime(t.getArchivedtime());
				}
				o.setOrdernum(t.getOrdernum());
				o.setOrderCategory(t.getTOrderCategory().getName());
				o.setTitle(t.getTitle());
				o.setAuthor(t.getTAccountByAuthorid().getUsername());
				o.setCreatetime(t.getCreatetime());
				if (t.getTAccountByModifyid()!=null){
					o.setEditor(t.getTAccountByModifyid().getUsername());
				}
				o.setModifytime(t.getModifytime());
				o.setContent(t.getContent());
				orderList.add(o);
			}
		}

		dg.setRows(orderList);

		// 查询结果记录数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(orderDao.count(totalHql));
		logger.info("共找到" + dg.getTotal() + "条记录");

		return dg;
	}

	@Override
	public List<OrderType> getOrderType() {
		List<OrderType> typeList = new ArrayList<OrderType>();
		// 获取管控类型
		String hql = "from TOrderType t";
		List<TOrderType> list = orderTypeDao.find(hql);
		for (TOrderType t : list) {
			OrderType o = new OrderType();
			BeanUtils.copyProperties(t, o);
			typeList.add(o);
		}
		return typeList;
	}

	@Override
	public List<OrderCategory> getOrderCategory() {
		List<OrderCategory> typeList = new ArrayList<OrderCategory>();
		// 获取管控类型
		String hql = "from TOrderCategory t";
		List<TOrderCategory> list = orderCategoryDao.find(hql);
		for (TOrderCategory t : list) {
			OrderCategory o = new OrderCategory();
			BeanUtils.copyProperties(t, o);
			typeList.add(o);
		}
		return typeList;
	}

	@Override
	public Order add(Order order, SessionInfo author) {
		TOrder t = new TOrder();
		t.setId(UUID.randomUUID().toString());
		TOrderType tOrderType = new TOrderType(order.getTypeid());
		t.setTOrderType(tOrderType);

		// 设置工单接单人
		TAccount tAccount = new TAccount();
		tAccount.setId(author.getUser().getId());
		t.setTAccountByAuthorid(tAccount);

		TOrderCategory tOrderCategory = new TOrderCategory(order.getOrderCategory());
		t.setTOrderCategory(tOrderCategory);
		t.setTitle(order.getTitle());
		t.setOrdernum(order.getOrdernum());
		t.setContent(order.getContent());
		t.setCreatetime(new Date());
		t.setNoticetime(order.getNoticetime());
		t.setIsArchived("在途");
		orderDao.save(t);
		return order;
	}

	@Override
	public int archivedOrder(String ids) {
		int sum = 0;
		String[] id = ids.split(",");
		for (String tempId : id) {
			TOrder t = orderDao.getForId(TOrder.class, tempId);
			if (t != null && !t.getIsArchived().equals("已归档")) {
				sum++;
				t.setIsArchived("已归档");
				t.setArchivedtime(new Date());
				orderDao.saveOrUpdate(t);
			}
		}

		return sum;
	}

	@Override
	public Order edit(Order order, SessionInfo editor) {
		TOrder t = orderDao.getForId(TOrder.class, order.getId());

		String hql = "from TOrderType t where t.name='" + order.getTypeid() + "'";
		TOrderType tOrderType = orderTypeDao.get(hql);
		if (tOrderType == null) {
			tOrderType = new TOrderType(order.getTypeid());
			t.setTOrderType(tOrderType);
		}

		t.setNoticetime(order.getNoticetime());
		t.setOrdernum(order.getOrdernum());
		t.setTitle(order.getTitle());

		hql = "from TOrderCategory t where t.name='" + order.getOrderCategory() + "'";
		TOrderCategory tOrderCategory = orderCategoryDao.get(hql);
		if (tOrderCategory == null) {
			tOrderCategory = new TOrderCategory(order.getOrderCategory());
			t.setTOrderCategory(tOrderCategory);
		}

		t.setContent(order.getContent());
		if (order.getIsArchived().equals("已归档")) {
			t.setArchivedtime(new Date());
		}
		t.setIsArchived(order.getIsArchived());
		TAccount tAccount = new TAccount();
		tAccount.setId(editor.getUser().getId());
		t.setTAccountByModifyid(tAccount);
		t.setModifytime(new Date());
		return order;
	}

}
