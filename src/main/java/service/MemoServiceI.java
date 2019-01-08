package service;

import pageModel.DataGrid;
import pageModel.Memo;

public interface MemoServiceI {

	DataGrid<Memo> getAll(Memo memo);

	DataGrid<Memo> getLdjb(Memo memo);

	DataGrid<Memo> getPtjb(Memo memo);

	void save(Memo memo);

	void edit(Memo memo);

	void delete(String id);

	DataGrid<Memo> getUnfinish(Memo memo);

}
