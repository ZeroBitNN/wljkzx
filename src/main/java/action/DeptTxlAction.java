package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.DeptTxl;
import service.DeptTxlServiceI;

@Namespace("/")
@Action(value = "deptTxlAction")
public class DeptTxlAction extends BaseAction implements ModelDriven<DeptTxl> {
	private DeptTxl deptTxl = new DeptTxl();

	@Override
	public DeptTxl getModel() {
		return deptTxl;
	}
	
	private DeptTxlServiceI deptTxlService;

	public DeptTxlServiceI getDeptTxlService() {
		return deptTxlService;
	}

	@Autowired
	public void setDeptTxlService(DeptTxlServiceI deptTxlService) {
		this.deptTxlService = deptTxlService;
	}
	

	public void doNotNeedSecurity_datagrid(){
		DataGrid<DeptTxl> dg = deptTxlService.getDatagrid(deptTxl);
		super.writeJson(dg);
	}
}
