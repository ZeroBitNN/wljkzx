package dao.impl;

import org.springframework.stereotype.Repository;

import dao.WorkrecordDaoI;
import model.TWorkrecord;

@Repository("workrecordDao")
public class WorkrecordDaoImpl extends BaseDaoImpl<TWorkrecord> implements WorkrecordDaoI {

}
