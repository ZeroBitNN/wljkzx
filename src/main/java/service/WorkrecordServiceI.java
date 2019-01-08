package service;

import java.util.List;

import pageModel.DataGrid;
import pageModel.RecReport;
import pageModel.Workrecord;

public interface WorkrecordServiceI {

	DataGrid<Workrecord> getDatagrid(Workrecord workrecord);

	void save(Workrecord workrecord);

	void edit(Workrecord workrecord);

	void delete(String id);

	List<RecReport> getReport(RecReport recReport);

	DataGrid<RecReport> getReportDg(RecReport recReport);

	DataGrid<Workrecord> getUntreated(Workrecord workrecord, String username);

	void updateHandler(Workrecord workrecord);

}
