package dao.impl;

import org.springframework.stereotype.Repository;

import dao.OrderDaoI;
import model.TOrder;

@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<TOrder> implements OrderDaoI {

}
