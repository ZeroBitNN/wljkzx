package dao.impl;

import org.springframework.stereotype.Repository;

import dao.OwogImagesDaoI;
import model.TOwogImages;

@Repository("owogImagesDao")
public class OwogImagesDaoImpl extends BaseDaoImpl<TOwogImages> implements OwogImagesDaoI {

}
