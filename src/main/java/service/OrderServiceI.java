package service;

import java.util.List;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.Order;
import pageModel.OrderCategory;
import pageModel.OrderType;
import pageModel.TbReport;

public interface OrderServiceI {
	
	public DataGrid<Order> getAllOrders(Order order);

	public List<OrderType> getOrderType();

	public List<OrderCategory> getOrderCategory();

	public Order add(Order order, SessionInfo author);

	public int archivedOrder(String ids);

	public Order edit(Order order, SessionInfo editor);

}
