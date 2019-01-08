package dao.impl;

import org.springframework.stereotype.Repository;

import dao.LinksDaoI;
import model.TLinks;

@Repository("linksDao")
public class LinksDaoImpl extends BaseDaoImpl<TLinks> implements LinksDaoI{

}
