package dao.impl;

import org.springframework.stereotype.Repository;

import dao.NetManageDaoI;
import model.TNetmanage;

@Repository("netManageDao")
public class NetManageDaoImpl extends BaseDaoImpl<TNetmanage> implements NetManageDaoI {

}
