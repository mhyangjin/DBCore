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
public class FactoryDAOImpl extends GenericDAOImpl<Factory> {
	//Auto generated from DBCoreGenerator
	static String[] NotnullColumns= {"memberCorpid","factoryid","bizNo"};
	static String[][] limitSizeColumns= {{"memberCorpid","20"},
										{"factoryid","20"},
										{"bizNo","20"},
										{"factorytype","15"},
										};
	
	public FactoryDAOImpl () {
		super(Factory.class);
	}
	
	public boolean isValid(Factory currFactory ) {
		boolean checkResult =  notNullCheck(currFactory)  && columnSizeCheck(currFactory);
		//Exception throw 필요
		return checkResult;
	}
	
	public boolean notNullCheck(Factory currFactory ) {
		boolean checkResult=true;
		for ( int i=0; i < NotnullColumns.length; i++)
		{
			String getMethodName = "get" + NotnullColumns[i].toUpperCase().charAt(0) +  NotnullColumns[i].substring(1);
			try {
				String ColumnValue=(String) Factory.class.getMethod(getMethodName).invoke(currFactory);
				DBCoreLogger.printInfo("NotnullCheck:" + getMethodName + ":" + ColumnValue);
				if (ColumnValue.length() == 0 ) //Exception throw 필요
					return false;
			} catch (Exception e) {
				return false;
			}
		}
		return checkResult;
	}
	
	public boolean columnSizeCheck(Factory currFactory ) {
		boolean checkResult=true;
		for ( int i=0; i < limitSizeColumns.length; i++)
		{
			String columnName=limitSizeColumns[i][0];
			int columnSize=Integer.parseInt(limitSizeColumns[i][1]);
			
			String getMethodName = "get" + columnName.toUpperCase().charAt(0) +  columnName.substring(1);
			try {
				String ColumnValue=(String) Factory.class.getMethod(getMethodName).invoke(currFactory);
				DBCoreLogger.printInfo("SizeCheck:" + getMethodName + ":" + ColumnValue + ":" + columnSize);
				if (ColumnValue.length() > columnSize ) //Exception throw 필요
					return false;
			} catch (Exception e) {
				return false;
			}
		}
		return checkResult;
	}
	
}
