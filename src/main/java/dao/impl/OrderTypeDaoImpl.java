package dao.impl;

import org.springframework.stereotype.Repository;

import dao.OrderTypeDaoI;
import model.TOrderType;

@Repository("orderTypeDao")
public class OrderTypeDaoImpl extends BaseDaoImpl<TOrderType> implements OrderTypeDaoI {

}
