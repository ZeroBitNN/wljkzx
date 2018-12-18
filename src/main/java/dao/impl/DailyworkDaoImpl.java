package dao.impl;

import org.springframework.stereotype.Repository;

import dao.DailyworkDaoI;
import model.TDailywork;

@Repository("dailyworkDao")
public class DailyworkDaoImpl extends BaseDaoImpl<TDailywork> implements DailyworkDaoI {

	public DailyworkDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyworkDaoImpl(org.hibernate.SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
