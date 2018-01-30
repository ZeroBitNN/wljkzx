package dao.impl;

import org.springframework.stereotype.Repository;

import dao.PerfParamDaoI;
import model.TPerfParam;

@Repository("perfParamDao")
public class PerfParamDaoImpl extends BaseDaoImpl<TPerfParam> implements PerfParamDaoI {

}
