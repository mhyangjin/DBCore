package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
  */
public class GenericKeyDAOImpl<T extends Serializable> implements GenericKeyDAO<T> {
	private Class<T> type;
	
	public GenericKeyDAOImpl(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public String[] getKeyColumns() {
			
		Field[] columns = type.getDeclaredFields();
		String[] columnNames=new String[columns.length];
		for ( int i=0; i < columns.length; i++) {
			columnNames[i]=columns[i].getName();
		}
		return columnNames;
	}

	@Override
	public boolean isValid(T object) {
		boolean vaild=true;
		Field[] columns = type.getDeclaredFields();
		for ( int i=0; i < columns.length; i++) {
			String ColumnName=columns[i].getName();
			String getMethodName="get" + ColumnName.toUpperCase().charAt(0) + ColumnName.substring(1);
			try {
				String Res=(String) type.getMethod(getMethodName).invoke(object);
				DBCoreLogger.printInfo("isValid:" + ColumnName + ":" + Res);
				if ( Res.length() <0 )
					vaild=false;
			} catch (Exception e) {
				vaild=false;
				break;
			}
		}
		return vaild;
		
	}
}
