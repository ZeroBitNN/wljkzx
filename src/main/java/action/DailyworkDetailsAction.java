package action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.DailyworkDetails;
import pageModel.DataGrid;
import pageModel.Json;
import service.DailyworkServiceI;

@Namespace("/")
@Action(value = "dailyworkDetailsAction")
public class DailyworkDetailsAction extends BaseAction implements ModelDriven<DailyworkDetails> {
	private static final Logger log = Logger.getLogger(DailyworkDetailsAction.class);
	private DailyworkDetails dailyworkDetails = new DailyworkDetails();

	@Override
	public DailyworkDetails getModel() {
		return dailyworkDetails;
	}

	private DailyworkServiceI dailyworkService;

	public DailyworkServiceI getDailyworkService() {
		return dailyworkService;
	}

	@Autowired
	public void setDailyworkService(DailyworkServiceI dailyworkService) {
		this.dailyworkService = dailyworkService;
	}

	private SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");

	public void doNotNeedSecurity_datagrid() {
		if (dailyworkDetails.getDateStr()!=null){
			//log.info(dailyworkDetails.getDateStr());
			//dailyworkDetails.setDailydate(new Date(dailyworkDetails.getDateStr()));
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
			try {
				dailyworkDetails.setDailydate(sdf.parse(dailyworkDetails.getDateStr()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		DataGrid<DailyworkDetails> dg = dailyworkService.getDetailsDg(dailyworkDetails);
		super.writeJson(dg);
	}

	public void doNotNeedSecurity_edit() {
		Json j = new Json();
		try {
			dailyworkService.editDetails(dailyworkDetails, sessionInfo);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改成功！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
