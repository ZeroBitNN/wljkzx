package dao.impl;

import org.springframework.stereotype.Repository;

import dao.MenuDaoI;
import model.Tmenu;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Tmenu> implements MenuDaoI {


}
