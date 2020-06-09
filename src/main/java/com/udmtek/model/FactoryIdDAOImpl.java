package com.udmtek.model;

import org.springframework.stereotype.Component;
import com.udmtek.DBCore.DAOModel.GenericKeyDAOImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component("FactoryIdDAOImpl")
public class FactoryIdDAOImpl extends GenericKeyDAOImpl<FactoryIdDAO> {
	public FactoryIdDAOImpl () {
		super(FactoryIdDAO.class);
	}
}
