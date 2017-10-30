package dao.impl;

import org.springframework.stereotype.Repository;

import dao.DailyworkDetailsDaoI;
import model.TDailyworkDetails;

@Repository("dailyworkDetailsDao")
public class DailyworkDetailsDaoImpl extends BaseDaoImpl<TDailyworkDetails> implements DailyworkDetailsDaoI {

}
