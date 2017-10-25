package action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Memo;
import service.MemoServiceI;

@Namespace("/")
@Action(value = "memoAction")
public class MemoAction extends BaseAction implements ModelDriven<Memo> {
	private static final Logger logger = Logger.getLogger(MemoAction.class);
	private Memo memo = new Memo();

	@Override
	public Memo getModel() {
		return memo;
	}

	private MemoServiceI memoService;

	public MemoServiceI getMemoService() {
		return memoService;
	}

	@Autowired
	public void setMemoService(MemoServiceI memoService) {
		this.memoService = memoService;
	}

	public void doNotNeedSecurity_getAllDatagrid() {
		DataGrid<Memo> g = memoService.getAll(memo);
		super.writeJson(g);
	}

	public void doNotNeedSecurity_getLdjbDatagrid() {
		DataGrid<Memo> g = memoService.getLdjb(memo);
		super.writeJson(g);
	}

	public void doNotNeedSecurity_getPtjbDatagrid() {
		DataGrid<Memo> g = memoService.getPtjb(memo);
		super.writeJson(g);
	}
	
	public void doNotNeedSecurity_getUnfinish(){
		DataGrid<Memo> g = memoService.getUnfinish(memo);
		super.writeJson(g);
	}

	public void add() {
		Json j = new Json();
		try {
			memoService.save(memo);
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
			memoService.edit(memo);
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
			memoService.delete(memo.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
