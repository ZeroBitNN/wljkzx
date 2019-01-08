/**
 * 
 */
package action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.TbReport;
import service.OrderNoticeServiceI;
import service.UserServiceI;

/**
 * @Description:
 * @author 宋宝定
 * @CreateDate: 2018年12月17日
 *
 */

@Namespace("/")
@Action(value = "tbReportAction")
public class TbReportAction extends BaseAction implements ModelDriven<TbReport> {
	private static final Logger logger = Logger.getLogger(TbReportAction.class);

	private TbReport tbReport = new TbReport();

	@Override
	public TbReport getModel() {
		return tbReport;
	}

	private OrderNoticeServiceI orderNoticeService;

	public OrderNoticeServiceI getOrderNoticeService() {
		return orderNoticeService;
	}

	@Autowired
	public void setOrderNoticeService(OrderNoticeServiceI orderNoticeService) {
		this.orderNoticeService = orderNoticeService;
	}
	
	public void doNotNeedSecurity_getReport() {
		DataGrid<TbReport>  rData = orderNoticeService.getReportDg(tbReport);
		super.writeJson(rData);
	}
}
