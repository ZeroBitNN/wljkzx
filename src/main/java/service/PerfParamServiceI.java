package service;

import pageModel.DataGrid;
import pageModel.PerfParam;

public interface PerfParamServiceI {

	DataGrid<PerfParam> getParams();

	void addOrUpdate(PerfParam perfParam);

	void delete(PerfParam perfParam) throws Exception;

}
