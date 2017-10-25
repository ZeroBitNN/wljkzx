package dao.impl;

import org.springframework.stereotype.Repository;

import dao.MemoDaoI;
import model.TMemo;

@Repository("memoDao")
public class MemoDaoImpl extends BaseDaoImpl<TMemo> implements MemoDaoI {

}
