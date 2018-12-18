package action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import kmbsModel.KbmsCategory;
import pageModel.Json;
import service.KbmsCategoryServiceI;

@Action(value = "kbmsCategoryAction")
public class KbmsCategoryAction extends BaseAction implements ModelDriven<KbmsCategory> {

	private KbmsCategory kbmsCategory = new KbmsCategory();

	@Override
	public KbmsCategory getModel() {
		return kbmsCategory;
	}

	public KbmsCategoryServiceI kbmsCategoryService;

	public KbmsCategoryServiceI getKbmsCategoryService() {
		return kbmsCategoryService;
	}

	@Autowired
	public void setKbmsCategoryService(KbmsCategoryServiceI kbmsCategoryService) {
		this.kbmsCategoryService = kbmsCategoryService;
	}

	public void getTree() {
		try {
			List<KbmsCategory> treeList = kbmsCategoryService.getTree(kbmsCategory.getId());
			super.writeJson(treeList);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void edit() {
		Json j = new Json();
		try {
			KbmsCategory kc = kbmsCategoryService.addOrUpdate(kbmsCategory);
			j.setSuccess(true);
			j.setObj(kc);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			j.setMsg("编辑失败！");
		}
		super.writeJson(j);
	}
	
	public void delete(){
		Json j = new Json();
		try {
			kbmsCategoryService.delete(kbmsCategory);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！");
		}
		super.writeJson(j);
	}
}
