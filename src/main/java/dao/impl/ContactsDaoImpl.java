package dao.impl;

import org.springframework.stereotype.Repository;

import dao.ContactsDaoI;
import model.TContacts;

@Repository("contactsDao")
public class ContactsDaoImpl extends BaseDaoImpl<TContacts> implements ContactsDaoI {

}
