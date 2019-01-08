package action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import service.RepairServiceI;

@Action(value = "repairAction")
public class RepairAction extends BaseAction {
	private RepairServiceI repairService;

	public RepairServiceI getRepairService() {
		return repairService;
	}

	@Autowired
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	public void init() {
		repairService.init();
	}

	public void repairAdmin() {
		repairService.repairUser();
	}

	public void initMenu() {
		repairService.initMenu();
	}
	
	public void initResources(){
		repairService.initRes();
	}

	public void doNotNeedSecurity_initAdmin(){
		repairService.repairAdmin();
	}
	
	public void initPerfParam(){
		repairService.initPerfParam();
	}
}
