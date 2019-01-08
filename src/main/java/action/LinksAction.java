package action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.Json;
import pageModel.Links;
import pageModel.LinksDataList;
import service.LinksServiceI;

@Namespace("/")
@Action(value = "linksAction")
public class LinksAction extends BaseAction implements ModelDriven<Links> {
	private static final Logger logger = Logger.getLogger(LinksAction.class);
	private Links links = new Links();

	@Override
	public Links getModel() {
		return links;
	}

	private LinksServiceI linksService;

	public LinksServiceI getLinksService() {
		return linksService;
	}

	@Autowired
	public void setLinksService(LinksServiceI linksService) {
		this.linksService = linksService;
	}

	public void doNotNeedSecurity_linksDataList() {
		List<LinksDataList> list = linksService.getDataList(links);
		super.writeJson(list);
	}

	public void datagrid() {
		List<Links> list = linksService.getDataGrid();
		super.writeJson(list);
	}

	public void add() {
		Json j = new Json();
		try {
			linksService.save(links);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			linksService.edit(links);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void delete() {
		Json j = new Json();
		try {
			linksService.delete(links.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
