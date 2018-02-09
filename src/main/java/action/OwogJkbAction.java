package action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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

import owogModel.OwogJkb;
import pageModel.Json;
import service.OwogJkbServiceI;
import util.ExcelUtil;
import util.FileUtil;
import util.StringUtil;

@Namespace("/")
@Action(value = "owogJkbAction")
public class OwogJkbAction extends BaseAction implements ModelDriven<OwogJkb> {
	private OwogJkb owogJkb = new OwogJkb();

	@Override
	public OwogJkb getModel() {
		return owogJkb;
	}

	private OwogJkbServiceI owogJkbService;

	public OwogJkbServiceI getOwogJkbService() {
		return owogJkbService;
	}

	@Autowired
	public void setOwogJkbService(OwogJkbServiceI owogJkbService) {
		this.owogJkbService = owogJkbService;
	}

	public void datagrid() {
		try {
			if (owogJkb.getRangetime() != null && !owogJkb.getRangetime().equals("")) {
				// 将字符串乱码转换为UTF-8
				String rangeTime = new String(owogJkb.getRangetime().getBytes("iso8859-1"), "utf-8");
				owogJkb.setRangetime(rangeTime);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<OwogJkb> l = owogJkbService.getDatagrid(owogJkb);
		super.writeJson(l);
	}

	public void doNotNeedSecurity_downloadExcel() {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
				.get(StrutsStatics.HTTP_RESPONSE);
		String filePathName = ServletActionContext.getServletContext().getRealPath("/")
				+ "owog/template/支撑班一周一标杆录入模板.xlsx";
		FileUtil.downloadExcelFromTemplate(filePathName, response);
	}

	public void doNotNeedSecurity_exportExcel() {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
				.get(StrutsStatics.HTTP_RESPONSE);

		// 设置表头
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("startdate", "周期开始日期");
		headMap.put("enddate", "周期结束日期");
		headMap.put("name", "姓名");
		headMap.put("zb11", "指标1：班组计件类工单处理量【包括：IOM&ISAP（新旧）工单、综保工单、电子运维故障工单等】（占30分）-本周完成情况（占70%）");
		headMap.put("zb12", "指标1：班组计件类工单处理量【包括：IOM&ISAP（新旧）工单、综保工单、电子运维故障工单等】（占30分）-环比提升情况（占30%）");
		headMap.put("zb21", "指标2：网络大面积故障管控及网络割接管控量【正常管控未出现管控缺失】（占30分）-本周完成情况（占70%）");
		headMap.put("zb22", "指标2：网络大面积故障管控及网络割接管控量【正常管控未出现管控缺失】（占30分）-环比提升情况（占30%）");
		headMap.put("zb31", "指标3：班组工位综运单、电子运维生产任务单处理 （占20分）-本周完成情况（占70%）");
		headMap.put("zb32", "指标3：班组工位综运单、电子运维生产任务单处理 （占20分）-环比提升情况（占30%）");
		headMap.put("zb41", "指标4：领导交办事件处理（占10分）-本周完成情况（占70%）");
		headMap.put("zb42", "指标4：领导交办事件处理（占10分）-环比提升情况（占30%）");
		headMap.put("ldpf", "领导评分（10分）");
		headMap.put("jf1", "加分指标-岗位创新");
		headMap.put("jf2", "加分指标-分公司重大专项");
		headMap.put("jf3", "加分指标-倒三角服务确认书");
		headMap.put("jf4", "加分指标-重要客户表扬");
		headMap.put("jf5", "加分指标-积极参加工会/部门等团体活动");
		headMap.put("jf6", "加分指标-因班组工作安排临时加班/顶班");
		headMap.put("ypfj1", "一票否决指标-考勤缺勤、工休、外出培训、无故迟到、旷工");
		headMap.put("ypfj2", "一票否决指标-重要客户投诉");
		headMap.put("ypfj3", "一票否决指标-服务事件");
		headMap.put("ypfj4", "一票否决指标-工单处理（班组承接的各类工单）延误、错误等导致其它部门投诉、或上级领导提出问题");
		headMap.put("ypfj5", "一票否决指标-网络大面积故障管控、网络割接管控缺失（包括工单管控反馈、易信通报要求等）");

		// 设置数据集
		JSONArray ja = new JSONArray();
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("startdate", "格式范例：" + StringUtil.getMonday(new Date()));
		dataMap.put("enddate", "格式范例：" + StringUtil.getWeekend(new Date()));
		dataMap.put("name", "张三");
		dataMap.put("zb11", "");
		dataMap.put("zb12", "");
		dataMap.put("zb21", "");
		dataMap.put("zb22", "");
		dataMap.put("zb31", "");
		dataMap.put("zb32", "");
		dataMap.put("zb41", "");
		dataMap.put("zb42", "");
		dataMap.put("ldpf", "");
		dataMap.put("jf1", "");
		dataMap.put("jf2", "");
		dataMap.put("jf3", "");
		dataMap.put("jf4", "");
		dataMap.put("jf5", "");
		dataMap.put("jf6", "");
		dataMap.put("ypfj1", "格式：是/否");
		dataMap.put("ypfj2", "否");
		dataMap.put("ypfj3", "否");
		dataMap.put("ypfj4", "否");
		dataMap.put("ypfj5", "否");
		ja.add(dataMap);

		// 导出EXCEL
		ExcelUtil.downloadExcelFile("支撑班一周一标杆录入模板(" + StringUtil.getWeekStartEnd(new Date()) + ")", headMap, ja,
				response);
	}

	public void edit() {
		Json j = new Json();
		try {
			owogJkbService.edit(owogJkb);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void importExcel() {
		Json j = owogJkbService.importExcel(ServletActionContext.getRequest());
		super.writeJson(j);
	}

	public void calc() {
		Json j = new Json();
		try {
			owogJkbService.calc();
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("计算失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void doNotNeedSecurity_getRangetime() {
		JSONArray ja = new JSONArray();
		List<String> sList = owogJkbService.getRangetime();
		if (sList != null && sList.size() > 0) {
			for (String s : sList) {
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				dataMap.put("rangetime", s);
				ja.add(dataMap);
			}
		}
		super.writeJson(ja);
	}
}
