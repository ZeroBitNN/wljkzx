package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import owogModel.OwogJkb;
import pageModel.Json;

public interface OwogJkbServiceI {

	List<OwogJkb> getDatagrid(OwogJkb owogJkb);

	void edit(OwogJkb owogJkb);

	Json importExcel(HttpServletRequest request);

	void calc() throws Exception;

	List<String> getRangetime();

}
