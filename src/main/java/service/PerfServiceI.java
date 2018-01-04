package service;

import pageModel.DataGrid;
import pageModel.Perf;

public interface PerfServiceI {

	DataGrid<Perf> getGrjx();

	void saveGrjx(Perf perf);

	DataGrid<Perf> getPerf();

}
