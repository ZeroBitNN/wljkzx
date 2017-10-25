package dao.impl;

import org.springframework.stereotype.Repository;

import dao.UserDaoI;
import model.TAccount;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<TAccount> implements UserDaoI {

}
