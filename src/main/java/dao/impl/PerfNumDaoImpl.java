package dao.impl;

import org.springframework.stereotype.Repository;

import dao.PerfNumDaoI;
import model.TPerfNum;

@Repository("perfNumDao")
public class PerfNumDaoImpl extends BaseDaoImpl<TPerfNum> implements PerfNumDaoI {

}
