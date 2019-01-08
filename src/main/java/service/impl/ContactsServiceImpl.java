package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ContactsDaoI;
import model.TContacts;
import pageModel.Contacts;
import pageModel.DataGrid;
import service.ContactsServiceI;

@Service(value = "contactsService")
public class ContactsServiceImpl implements ContactsServiceI {

	private static final Logger logger = Logger.getLogger(ContactsServiceImpl.class);

	private ContactsDaoI contactsDao;

	public ContactsDaoI getContactsDao() {
		return contactsDao;
	}

	@Autowired
	public void setContactsDao(ContactsDaoI contactsDao) {
		this.contactsDao = contactsDao;
	}

	@Override
	public List<Contacts> getAll(Contacts contacts) {
		List<Contacts> list = new ArrayList<Contacts>();
		String hql = "from TContacts t where 1=1";
		if (contacts.getName() != null && !contacts.getName().trim().equals("")) {
			hql += " and t.name like '%" + contacts.getName() + "%'";
		}
		List<TContacts> tList = contactsDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TContacts t : tList) {
				Contacts c = new Contacts();
				BeanUtils.copyProperties(t, c);
				list.add(c);
			}
		}

		return list;
	}

	@Override
	public void save(Contacts contacts) {
		TContacts t = new TContacts();
		BeanUtils.copyProperties(contacts, t);
		contactsDao.save(t);
	}

	@Override
	public void edit(Contacts contacts) {
		TContacts t = contactsDao.getForId(TContacts.class, contacts.getId());
		BeanUtils.copyProperties(contacts, t, new String[] { "id" });
	}

	@Override
	public void delete(Contacts contacts) {
		if (contacts.getId() != null && !contacts.getId().trim().equals("")) {
			String hql = "delete from TContacts t where t.id='" + contacts.getId() + "'";
			contactsDao.executeHql(hql);
		}
	}
}
