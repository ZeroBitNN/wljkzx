package dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.BaseDaoI;

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	public BaseDaoImpl() {

	}

	public BaseDaoImpl(org.hibernate.SessionFactory sessionFactory) {
		super();
		SessionFactory = sessionFactory;
	}

	private SessionFactory SessionFactory;

	private Session getCurrentSession() {
		return this.SessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return SessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		SessionFactory = sessionFactory;
	}

	@Override
	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	@Override
	public T get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}

	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T getForId(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	@Override
	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	@Override
	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	@Override
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int excuteSql(String sql) {
		this.getCurrentSession().beginTransaction().commit();
		int r;
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		r = query.executeUpdate();
		return r;
	}

}
