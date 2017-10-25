package service;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.OrderNotice;

public interface OrderNoticeServiceI {

	DataGrid<OrderNotice> getAll(OrderNotice orderNotice);

	OrderNotice add(OrderNotice orderNotice, SessionInfo sessionInfo);

	void delete(String id);

}
