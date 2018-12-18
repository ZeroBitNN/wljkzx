package action;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.Demand;
import pageModel.Json;
import service.DemandServiceI;

@Namespace("/")
@Action(value = "demandAction")
public class DemandAction extends BaseAction implements ModelDriven<Demand> {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(DemandAction.class);
	
	private Demand demand = new Demand();

	@Override
	public Demand getModel() {
		return demand;
	}

	private DemandServiceI demandService;

	public DemandServiceI getDemandService() {
		return demandService;
	}

	@Autowired
	public void setDemandService(DemandServiceI demandService) {
		this.demandService = demandService;
	}

	private SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");

	public void doNotNeedSecurity_datagrid() {
		DataGrid<Demand> g = demandService.getDataGrid(demand);
		super.writeJson(g);
	}

	public void doNotNeedSecurity_listForShow() {
		List<Demand> list = demandService.getListForShow();
		super.writeJson(list);
	}

	public void add() {
		Json j = new Json();
		if (sessionInfo != null) {
			try {
				Demand d = demandService.save(demand, sessionInfo.getUser());
				j.setSuccess(true);
				j.setMsg("添加成功！");
				j.setObj(d);
			} catch (Exception e) {
				j.setMsg("添加失败！" + e.getMessage());
			}
		} else {
			j.setMsg("用户未登陆或登陆已失效！请重新登陆。");
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		if (sessionInfo != null) {
			try {
				Demand d = demandService.update(demand, sessionInfo.getUser());
				j.setSuccess(true);
				j.setMsg("修改成功！");
				j.setObj(d);
			} catch (Exception e) {
				j.setMsg("修改失败！" + e.getMessage());
			}
		} else {
			j.setMsg("用户未登陆或登陆已失效！请重新登陆。");
		}
		super.writeJson(j);
	}

	public void delete() {
		Json j = new Json();
		try {
			demandService.delete(demand.getId());
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
