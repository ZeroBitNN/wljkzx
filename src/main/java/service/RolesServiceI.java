package service;

import pageModel.DataGrid;
import pageModel.Roles;

public interface RolesServiceI {

	DataGrid<Roles> getRoles(Roles roles);

	Roles edit(Roles roles);

	Roles add(Roles roles);

	void delete(String id);

}
