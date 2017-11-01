package dao.impl;

import org.springframework.stereotype.Repository;

import dao.DailyworkDaoI;
import model.TDailywork;

@Repository("dailyworkDao")
public class DailyworkDaoImpl extends BaseDaoImpl<TDailywork> implements DailyworkDaoI {

}
