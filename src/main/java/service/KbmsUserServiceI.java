package service;

import kmbsModel.KbmsUser;
import pageModel.DataGrid;

public interface KbmsUserServiceI {

	DataGrid<KbmsUser> getUser(KbmsUser kbmsUser);

	KbmsUser save(KbmsUser kbmsUser);

	KbmsUser edit(KbmsUser kbmsUser);

	void delete(KbmsUser kbmsUser);

}
