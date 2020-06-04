package com.udmtek.model;
 
import org.springframework.stereotype.Repository;

import com.udmtek.DBCore.DAOModel.GenericDAOImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Repository("FactoryDAOImpl")
public class FactoryDAOImpl extends GenericDAOImpl<Factory> {
	public FactoryDAOImpl () {
		super(Factory.class);
	}
}
