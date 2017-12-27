package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.Json;
import pageModel.PerfLevel;
import service.PerfParamServiceI;

@Namespace("/")
@Action(value = "perfLevelAction")
public class PerfLevelAction extends BaseAction implements ModelDriven<PerfLevel> {
	private PerfLevel perfLevel = new PerfLevel();

	@Override
	public PerfLevel getModel() {
		return perfLevel;
	}

	private PerfParamServiceI perfParamService;

	public PerfParamServiceI getPerfParamService() {
		return perfParamService;
	}

	@Autowired
	public void setPerfParamService(PerfParamServiceI perfParamService) {
		this.perfParamService = perfParamService;
	}

	public void doNotNeedSecurity_saveLevel() {
		Json j = new Json();
		try {
			perfParamService.saveLevel(perfLevel);
			j.setSuccess(true);
			j.setMsg("保存成功！");
		} catch (Exception e) {
			j.setMsg("保存失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
