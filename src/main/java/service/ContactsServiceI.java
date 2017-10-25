package service;

import java.util.List;

import pageModel.Contacts;

public interface ContactsServiceI {

	List<Contacts> getAll(Contacts contacts);

	void save(Contacts contacts);

	void edit(Contacts contacts);

	void delete(Contacts contacts);

}
