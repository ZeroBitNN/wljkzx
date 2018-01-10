package service;

import java.util.List;

import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.PerfLevel;
import pageModel.PerfParam;

public interface PerfParamServiceI {

	DataGrid<PerfParam> getParams();

	void addOrUpdate(PerfParam perfParam);

	void delete(PerfParam perfParam) throws Exception;

	PerfLevel getLevel();

	void saveLevel(PerfLevel perfLevel) throws IllegalArgumentException, IllegalAccessException;

	List<TPerfParam> getAllItems();

	List<String> getItemList();

	StringBuffer getLevelIntro();

	TPerfParam getParam(String id);

}
