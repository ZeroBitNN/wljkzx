package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.DeptJjbb;
import service.DeptJjbbServiceI;

@Namespace("/")
@Action(value = "deptJjbbAction")
public class DeptJjbbAction extends BaseAction implements ModelDriven<DeptJjbb> {
	DeptJjbb deptJjbb = new DeptJjbb();

	@Override
	public DeptJjbb getModel() {
		return deptJjbb;
	}

	private DeptJjbbServiceI deptJjbbService;
	
	public DeptJjbbServiceI getDeptJjbbService() {
		return deptJjbbService;
	}

	@Autowired
	public void setDeptJjbbService(DeptJjbbServiceI deptJjbbService) {
		this.deptJjbbService = deptJjbbService;
	}

	public void doNotNeedSecurity_Datagrid(){
		DataGrid<DeptJjbb> dg = deptJjbbService.getDatagrid(deptJjbb);
		super.writeJson(dg);
	}
}
