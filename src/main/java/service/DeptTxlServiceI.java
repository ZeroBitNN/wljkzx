package service;

import pageModel.DataGrid;
import pageModel.DeptTxl;

public interface DeptTxlServiceI {

	DataGrid<DeptTxl> getDatagrid(DeptTxl deptTxl);

}
