package action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.NetManage;
import service.NetManageServiceI;

@Namespace("/")
@Action(value = "netManageAction")
public class NetManageAction extends BaseAction implements ModelDriven<NetManage> {
	private NetManage netManage = new NetManage();
	@Override
	public NetManage getModel() {
		return netManage;
	}
	
	private static final Logger logger = Logger.getLogger(UserAction.class);
	
	private NetManageServiceI netManageService;
	public NetManageServiceI getNetManageService() {
		return netManageService;
	}
	@Autowired
	public void setNetManageService(NetManageServiceI netManageService) {
		this.netManageService = netManageService;
	}
	
	public void add(){
		Json j = new Json();

		try {
			NetManage net = netManageService.save(netManage);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(net);
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}

		super.writeJson(j);
	}
	
	public void datagrid() {
		DataGrid<NetManage> g = netManageService.getAll(netManage);
		super.writeJson(g);
	}
	
	public void delete(){
		Json j = new Json();

		try {
			netManageService.delete(netManage.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}

		super.writeJson(j);		
	}
	
	public void edit(){
		Json j = new Json();
		try {
			NetManage n = netManageService.edit(netManage);
			j.setSuccess(true);
			j.setObj(n);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}

		super.writeJson(j);	
	}
}
