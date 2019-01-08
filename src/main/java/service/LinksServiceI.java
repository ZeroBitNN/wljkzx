package service;

import java.util.List;

import pageModel.Links;
import pageModel.LinksDataList;

public interface LinksServiceI {

	List<LinksDataList> getDataList(Links links);

	List<Links> getDataGrid();

	void save(Links links);

	void delete(String id);

	void edit(Links links);

}
