package service;

import java.util.List;

import pageModel.DataGrid;
import pageModel.Demand;
import pageModel.User;

public interface DemandServiceI {

	DataGrid<Demand> getDataGrid(Demand demand);

	List<Demand> getListForShow();

	Demand save(Demand demand, User user);

	Demand update(Demand demand, User user);

	void delete(String id);

}
