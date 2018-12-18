package dao.impl;

import org.springframework.stereotype.Repository;

import dao.PerfDaoI;
import model.TPerf;

@Repository("perfDao")
public class PerfDaoImpl extends BaseDaoImpl<TPerf> implements PerfDaoI {

}
