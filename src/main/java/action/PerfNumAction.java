package action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.PerfNum;
import pageModel.User;
import service.PerfNumServiceI;
import service.PerfParamServiceI;
import service.UserServiceI;
import util.ExcelUtil;

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

	private PerfParamServiceI PerfParamService;

	public PerfParamServiceI getPerfParamService() {
		return PerfParamService;
	}

	@Autowired
	public void setPerfParamService(PerfParamServiceI perfParamService) {
		PerfParamService = perfParamService;
	}

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
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

	public void doNotNeedSecurity_exportExcel() throws ClassNotFoundException {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
				.get(StrutsStatics.HTTP_RESPONSE);

		// 设置表头
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("name", "姓名");
		List<TPerfParam> itemList = PerfParamService.getAllItems();
		if (itemList != null && itemList.size() > 0) {
			for (TPerfParam t : itemList) {
				headMap.put(t.getName(), t.getName());
			}
		}

		// 获取数据集
		JSONArray ja = new JSONArray();
		// 获取数据
		List<User> userList = userService.getUsers();
		if (userList != null && userList.size() > 0) {
			for (User u : userList) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("name", u.getUsername());
				if (itemList != null && itemList.size() > 0) {
					for (TPerfParam t : itemList) {
						dataMap.put(t.getName(), "");
					}
				}
				ja.add(dataMap);
			}
		}

		// 导出EXCEL
		ExcelUtil.downloadExcelFile("工单量录入模板", headMap, ja, response);
	}
	
	public void doNotNeedSecurity_importExcel(){
		Json j = perfNumService.importExcel(ServletActionContext.getRequest());
		super.writeJson(j);
	}
}
