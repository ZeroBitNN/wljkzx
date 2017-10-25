package dao.impl;

import org.springframework.stereotype.Repository;

import dao.RolesDaoI;
import model.TRoles;

@Repository("rolesDao")
public class RolesDaoImpl extends BaseDaoImpl<TRoles> implements RolesDaoI {

}
