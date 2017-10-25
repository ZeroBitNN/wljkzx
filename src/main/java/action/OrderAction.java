package action;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Order;
import pageModel.OrderCategory;
import pageModel.OrderType;
import service.OrderServiceI;

@Namespace("/")
@Action(value = "orderAction")
public class OrderAction extends BaseAction implements ModelDriven<Order> {
	private Order order = new Order();

	@Override
	public Order getModel() {
		return order;
	}

	private OrderServiceI orderService;

	public OrderServiceI getOrderService() {
		return orderService;
	}

	@Autowired
	public void setOrderService(OrderServiceI orderService) {
		this.orderService = orderService;
	}

	public void datagrid() {
		DataGrid<Order> g = orderService.getAllOrders(order);
		super.writeJson(g);
	}

	public void doNotNeedSecurity_getOrderType() {
		List<OrderType> list = orderService.getOrderType();
		super.writeJson(list);
	}

	public void doNotNeedSecurity_getOrderCategory() {
		List<OrderCategory> list = orderService.getOrderCategory();
		super.writeJson(list);
	}

	public void addOrder() {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");

		try {
			if (sessionInfo != null) {
				Order o = orderService.add(order, sessionInfo);
				j.setSuccess(true);
				j.setMsg("添加成功！");
				j.setObj(o);
			}
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void archived() {
		Json j = new Json();
		String ids = order.getIds();

		int result = orderService.archivedOrder(ids);
		if (result != 0) {
			j.setSuccess(true);
			j.setMsg("共 " + result + " 条记录归档成功！");
		} else {
			j.setMsg("归档失败！没有需要归档的工单。");
		}
		super.writeJson(j);
	}

	public void editOrder() {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");

		try {
			if (sessionInfo != null) {
				// 实现修改功能
				Order o = orderService.edit(order, sessionInfo);
				j.setSuccess(true);
				j.setMsg("修改成功！");
				j.setObj(o);
			}
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}

		super.writeJson(j);
	}
}
