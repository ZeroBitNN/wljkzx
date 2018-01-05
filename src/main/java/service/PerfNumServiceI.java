package service;

import javax.servlet.http.HttpServletRequest;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.PerfNum;

public interface PerfNumServiceI {

	DataGrid<PerfNum> getDatagrid(PerfNum perfNum);

	void saveNum(PerfNum perfNum);

	Json importExcel(HttpServletRequest request);

}
