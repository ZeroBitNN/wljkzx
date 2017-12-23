package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.PerfParam;
import service.PerfParamServiceI;

@Namespace("/")
@Action(value = "perfParamAction")
public class PerfParamAction extends BaseAction implements ModelDriven<PerfParam> {
	private PerfParam perfParam = new PerfParam();

	@Override
	public PerfParam getModel() {
		return perfParam;
	}

	private PerfParamServiceI perfParamService;

	public PerfParamServiceI getPerfParamService() {
		return perfParamService;
	}

	@Autowired
	public void setPerfParamService(PerfParamServiceI perfParamService) {
		this.perfParamService = perfParamService;
	}
	
	public void getParamTg(){
		DataGrid<PerfParam> tg = perfParamService.getParams();
		super.writeJson(tg);
	}
	
	public void doNotNeedSecurity_save(){
		Json j = new Json();
		try {
			perfParamService.addOrUpdate(perfParam);
			j.setSuccess(true);
			j.setMsg("保存成功！");
		} catch (Exception e) {
			j.setMsg("保存失败！"+e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void doNotNeedSecurity_delete(){
		Json j = new Json();
		try {
			perfParamService.delete(perfParam);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！"+e.getMessage());
		}
		super.writeJson(j);
	}
}
