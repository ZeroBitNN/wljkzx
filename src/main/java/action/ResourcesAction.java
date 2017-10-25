package action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.Resources;
import service.ResourcesServiceI;

@Action(value = "resourcesAction")
public class ResourcesAction extends BaseAction implements ModelDriven<Resources> {
	private Resources resources = new Resources();
	private ResourcesServiceI resourcesService;

	public ResourcesServiceI getResourcesService() {
		return resourcesService;
	}

	@Autowired
	public void setResourcesService(ResourcesServiceI resourcesService) {
		this.resourcesService = resourcesService;
	}

	@Override
	public Resources getModel() {
		return resources;
	}

	public void getRes() {
		List<Resources> l = resourcesService.getAllRes();
		super.writeJson(l);
	}

	public void doNotNeedSecurity_getResources() {
		List<Resources> l = null;
		if (resources.getId() != null && !resources.getId().trim().equals("")) {
			String roleId = resources.getId();
			l = resourcesService.getAllRes(roleId);
		} else {
			l = resourcesService.getAllRes();
		}
		super.writeJson(l);
	}

}
