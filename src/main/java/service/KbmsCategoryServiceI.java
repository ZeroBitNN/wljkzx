package service;

import java.util.List;

import kmbsModel.KbmsCategory;

public interface KbmsCategoryServiceI {

	List<KbmsCategory> getTree(String id);

	KbmsCategory addOrUpdate(KbmsCategory kbmsCategory);

	void delete(KbmsCategory kbmsCategory);

}
