package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.OrderNotice;
import service.OrderNoticeServiceI;

@Namespace("/")
@Action(value = "orderNoticeAction")
public class OrderNoticeAction extends BaseAction implements ModelDriven<OrderNotice> {
	private OrderNotice orderNotice = new OrderNotice();

	@Override
	public OrderNotice getModel() {
		return orderNotice;
	}

	private OrderNoticeServiceI orderNoticeService;

	public OrderNoticeServiceI getOrderNoticeService() {
		return orderNoticeService;
	}

	@Autowired
	public void setOrderNoticeService(OrderNoticeServiceI orderNoticeService) {
		this.orderNoticeService = orderNoticeService;
	}

	public void datagrid() {
		DataGrid<OrderNotice> g = orderNoticeService.getAll(orderNotice);
		super.writeJson(g);
	}

	public void addNotice() {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");
		
		try {
			if (sessionInfo != null) {
				OrderNotice o = orderNoticeService.add(orderNotice,sessionInfo);
				j.setSuccess(true);
				j.setMsg("添加成功！");
				j.setObj(o);
			}
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void delNotice(){
		Json j = new Json();

		try {
			orderNoticeService.delete(orderNotice.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}

		super.writeJson(j);
	}
}
