package dao.impl;

import org.springframework.stereotype.Repository;

import dao.DemandDaoI;
import model.TDemand;

@Repository("demandDao")
public class DemandDaoImpl extends BaseDaoImpl<TDemand> implements DemandDaoI {

}
