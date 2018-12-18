package service;

import java.util.List;

import pageModel.Resources;

public interface ResourcesServiceI {

	List<Resources> getAllRes();

	List<Resources> getAllRes(String roleId);
	

}
