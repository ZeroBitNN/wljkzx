package dao.impl;

import org.springframework.stereotype.Repository;

import dao.AttachmentDaoI;
import model.TAttachment;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends BaseDaoImpl<TAttachment> implements AttachmentDaoI {

}
