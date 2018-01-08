package service;

import javax.servlet.http.HttpServletRequest;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Perf;

public interface PerfServiceI {

	DataGrid<Perf> getGrjx();

	void saveGrjx(Perf perf);

	DataGrid<Perf> getPerf();

	Json importGrjx(HttpServletRequest request);

}
