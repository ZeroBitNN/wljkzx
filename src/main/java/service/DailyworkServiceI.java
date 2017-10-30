package service;

import pageModel.Dailywork;
import pageModel.DataGrid;

public interface DailyworkServiceI {

	DataGrid<Dailywork> getDatagrid(Dailywork dailywork);

	void save(Dailywork dailywork);

	void delete(String ids);

	void edit(Dailywork dailywork);

}
