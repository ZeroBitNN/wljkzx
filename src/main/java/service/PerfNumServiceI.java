package service;

import pageModel.DataGrid;
import pageModel.PerfNum;

public interface PerfNumServiceI {

	DataGrid<PerfNum> getDatagrid(PerfNum perfNum);

	void saveNum(PerfNum perfNum);

}
