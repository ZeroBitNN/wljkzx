package dao.impl;

import org.springframework.stereotype.Repository;

import dao.DailyworkDetailsDaoI;
import model.TDailyworkDetails;

@Repository("dailyworkDetailsDao")
public class DailyworkDetailsDaoImpl extends BaseDaoImpl<TDailyworkDetails> implements DailyworkDetailsDaoI {

	public DailyworkDetailsDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyworkDetailsDaoImpl(org.hibernate.SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
