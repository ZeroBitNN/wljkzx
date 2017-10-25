package dao.impl;

import org.springframework.stereotype.Repository;

import dao.ResourcesDaoI;
import model.TResources;

@Repository("resourcesDao")
public class ResourcesDaoImpl extends BaseDaoImpl<TResources> implements ResourcesDaoI {

}
