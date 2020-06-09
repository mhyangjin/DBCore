package com.udmtek.DBCore.model;
 
import org.springframework.stereotype.Repository;
import com.udmtek.DBCore.DAOModel.GenericDAOImpl;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Repository("FactoryImpl")
public class FactoryImpl extends GenericDAOImpl<FactoryInfo> {
public FactoryImpl () {
		super(FactoryInfo.class);
	}
}
