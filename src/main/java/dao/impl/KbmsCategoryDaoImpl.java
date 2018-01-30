package dao.impl;

import org.springframework.stereotype.Repository;

import dao.KbmsCategoryDaoI;
import model.TKbmsCategory;

@Repository("kbmsCategoryDao")
public class KbmsCategoryDaoImpl extends BaseDaoImpl<TKbmsCategory> implements KbmsCategoryDaoI {

}
