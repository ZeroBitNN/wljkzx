package dao.impl;

import org.springframework.stereotype.Repository;

import dao.GuaranteeNoticeDaoI;
import model.TGuaranteeNotice;

@Repository("guaranteeNoticeDao")
public class GuaranteeNoticeDaoImpl extends BaseDaoImpl<TGuaranteeNotice> implements GuaranteeNoticeDaoI {

}
