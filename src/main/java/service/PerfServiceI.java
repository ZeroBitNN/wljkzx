package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.TPerf;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Perf;

public interface PerfServiceI {

	DataGrid<Perf> getGrjx();

	void saveGrjx(Perf perf);

	DataGrid<Perf> getPerf(Perf perf);

	Json importGrjx(HttpServletRequest request);

	List<String> getPerfDate();

	List<TPerf> getPerfForDate(String perfdate);

}
