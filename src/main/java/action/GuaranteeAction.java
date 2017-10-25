package action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Guarantee;
import pageModel.Json;
import service.AttachmentServiceI;
import service.GuaranteeServiceI;

@Namespace("/")
@Action(value = "guaranteeAction")
public class GuaranteeAction extends BaseAction implements ModelDriven<Guarantee> {
	private Guarantee guarantee = new Guarantee();

	@Override
	public Guarantee getModel() {
		return guarantee;
	}

	private GuaranteeServiceI guaranteeService;
	private AttachmentServiceI attachmentService;

	public AttachmentServiceI getAttachmentService() {
		return attachmentService;
	}

	@Autowired
	public void setAttachmentService(AttachmentServiceI attachmentService) {
		this.attachmentService = attachmentService;
	}

	public GuaranteeServiceI getGuaranteeService() {
		return guaranteeService;
	}

	@Autowired
	public void setGuaranteeService(GuaranteeServiceI guaranteeService) {
		this.guaranteeService = guaranteeService;
	}

	public void doNotNeedSecurity_getAllDg() {
		DataGrid<Guarantee> g = guaranteeService.getGuaranteeDg(guarantee);
		super.writeJson(g);
	}

	public void doNotNeedSecurity_guaranteeDataList() {
		List<Guarantee> list = guaranteeService.getDataList(guarantee);
		super.writeJson(list);
	}

	public void doNotNeedSecurity_getGuarantee() {
		Guarantee g = guaranteeService.getGuarantee(guarantee.getId());
		super.writeJson(g);
	}

	public void add() {
		Json j = new Json();

		try {
			Guarantee g = guaranteeService.save(guarantee);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(g);
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void delete() {
		Json j = new Json();

		try {
			// 删除保障记录
			guaranteeService.delete(guarantee.getId());
			// 删除相关联的附件
			attachmentService.delByRelatedid(guarantee.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			Guarantee g = guaranteeService.edit(guarantee);
			j.setSuccess(true);
			j.setObj(g);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void stop(){
		Json j = new Json();
		try {
			guaranteeService.updateStatus(guarantee.getId());
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
