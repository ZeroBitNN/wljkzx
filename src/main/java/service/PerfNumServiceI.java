package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.TPerfNum;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.PerfNum;

public interface PerfNumServiceI {

	DataGrid<PerfNum> getDatagrid(PerfNum perfNum);

	void saveNum(PerfNum perfNum);

	Json importExcel(HttpServletRequest request);

	List<TPerfNum> getPerfNum(String id, String perfdate);

}
