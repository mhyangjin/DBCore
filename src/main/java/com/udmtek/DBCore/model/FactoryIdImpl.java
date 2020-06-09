package com.udmtek.DBCore.model;

import org.springframework.stereotype.Component;
import com.udmtek.DBCore.DAOModel.GenericKeyDAOImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component("FactoryIdImpl")
public class FactoryIdImpl extends GenericKeyDAOImpl<FactoryIdDAO> {
	public FactoryIdImpl () {
		super(FactoryIdDAO.class);
	}
}
