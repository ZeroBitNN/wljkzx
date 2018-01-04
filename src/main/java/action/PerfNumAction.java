package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.PerfNum;
import service.PerfNumServiceI;

@Namespace("/")
@Action(value = "perfNumAction")
public class PerfNumAction extends BaseAction implements ModelDriven<PerfNum> {
	private PerfNum perfNum = new PerfNum();

	@Override
	public PerfNum getModel() {
		return perfNum;
	}

	private PerfNumServiceI perfNumService;

	public PerfNumServiceI getPerfNumService() {
		return perfNumService;
	}

	@Autowired
	public void setPerfNumService(PerfNumServiceI perfNumService) {
		this.perfNumService = perfNumService;
	}

	public void getPerfNumDg() {
		DataGrid<PerfNum> dg = perfNumService.getDatagrid(perfNum);
		super.writeJson(dg);
	}

	public void doNotNeedSecurity_save() {
		Json j = new Json();
		try {
			perfNumService.saveNum(perfNum);
			j.setSuccess(true);
			j.setMsg("保存成功！");
		} catch (Exception e) {
			j.setMsg("保存失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
