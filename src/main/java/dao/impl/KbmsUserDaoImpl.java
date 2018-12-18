package dao.impl;

import org.springframework.stereotype.Repository;

import dao.KbmsUserDaoI;
import model.TKbmsUser;

@Repository("kbmsUserDao")
public class KbmsUserDaoImpl extends BaseDaoImpl<TKbmsUser> implements KbmsUserDaoI {

}
