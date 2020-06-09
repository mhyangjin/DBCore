package com.udmtek.model;
 
import java.lang.reflect.InvocationTargetException;
import org.springframework.stereotype.Repository;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DAOModel.GenericDAOImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Repository("FactoryDAOImpl")
public class FactoryDAOImpl extends FactoryDAO {
public FactoryDAOImpl () {
		super();
	}
}
