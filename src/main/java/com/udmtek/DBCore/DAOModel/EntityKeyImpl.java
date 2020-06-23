package com.udmtek.DBCore.DAOModel;

import java.lang.reflect.Field;
import java.util.Arrays;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
  */
public class EntityKeyImpl implements EntityKey {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2403101385254750155L;
	private Class<?> type;
	
	public EntityKeyImpl(Class<?> type) {
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
	public boolean isValid() {
		boolean vaild=true;
		Field[] columns = type.getDeclaredFields();
		for ( int i=0; i < columns.length; i++) {
			String ColumnName=columns[i].getName();
			String getMethodName="get" + ColumnName.toUpperCase().charAt(0) + ColumnName.substring(1);
			try {
				String Res=(String) type.getMethod(getMethodName).invoke(this);
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
	
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null ) return false;
		if ( o.getClass() != this.getClass() ) return false;
//		FactoryIdDAO oFactoryID=(FactoryIdDAO) o;
//		return  memberCorpid.equals(oFactoryID.memberCorpid) &&	factoryid.equals(oFactoryID.factoryid);
		return true;
	}
	
	@Override
	public int hashCode() {
		Field[] columns = type.getDeclaredFields();
		String[] columnValues= new String[columns.length];
		for ( int i=0; i < columns.length; i++) {
			String ColumnName=columns[i].getName();
			String getMethodName="get" + ColumnName.toUpperCase().charAt(0) + ColumnName.substring(1);
			try {
				columnValues[i]=(String) type.getMethod(getMethodName).invoke(this);
			} catch (Exception e) {
				break;
			}
		}
		return Arrays.hashCode(columnValues);
	}
}
