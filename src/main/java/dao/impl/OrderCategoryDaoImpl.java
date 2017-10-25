package dao.impl;

import org.springframework.stereotype.Repository;

import dao.OrderCategoryDaoI;
import model.TOrderCategory;

@Repository("orderCategoryDao")
public class OrderCategoryDaoImpl extends BaseDaoImpl<TOrderCategory> implements OrderCategoryDaoI {

}
