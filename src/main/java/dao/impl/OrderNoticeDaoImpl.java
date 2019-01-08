package dao.impl;

import org.springframework.stereotype.Repository;

import dao.OrderNoticeDaoI;
import model.TOrderNotice;

@Repository("orderNoticeDao")
public class OrderNoticeDaoImpl extends BaseDaoImpl<TOrderNotice> implements OrderNoticeDaoI {

}
