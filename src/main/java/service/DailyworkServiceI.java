package service;

import model.SessionInfo;
import pageModel.Dailywork;
import pageModel.DailyworkDetails;
import pageModel.DataGrid;

public interface DailyworkServiceI {

	DataGrid<Dailywork> getDatagrid(Dailywork dailywork);

	void save(Dailywork dailywork);

	void delete(String ids);

	void edit(Dailywork dailywork);

	DataGrid<DailyworkDetails> getDetailsDg(DailyworkDetails dailyworkDetails);

	void editDetails(DailyworkDetails dailyworkDetails, SessionInfo sessionInfo);

	String getUntreated();

}
