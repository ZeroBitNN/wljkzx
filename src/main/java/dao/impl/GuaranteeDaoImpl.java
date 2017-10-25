package dao.impl;

import org.springframework.stereotype.Repository;

import dao.GuaranteeDaoI;
import model.TGuarantee;

@Repository("guaranteeDao")
public class GuaranteeDaoImpl extends BaseDaoImpl<TGuarantee> implements GuaranteeDaoI {

}
