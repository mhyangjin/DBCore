package com.udmtek.DBCore.DAOModel;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.udmtek.DBCore.ComException.DBTypeException;
import com.udmtek.DBCore.ComException.InvalidNullableException;
/**
 * Implementation of the DBCoreEntityKey
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
  */
public class DBCoreEntityKeyImpl implements DBCoreEntityKey {
	private static Logger logger=LoggerFactory.getLogger(DBCoreEntityKeyImpl.class);
	
	private static final long serialVersionUID = -2403101385254750155L;
	private Class<?> type;
	
	/**
	 * constructor
	 * @param sub class type.
	 */
	public DBCoreEntityKeyImpl(Class<?> type) {
		this.type = type;
	}
	/**
	 * get column names 
	 * @return String[]
	 */
	@Override
	public String[] getKeyColumns() {
		Field[] columns = type.getDeclaredFields();
		String[] columnNames=new String[columns.length];
		for ( int i=0; i < columns.length; i++) {
			columnNames[i]=columns[i].getName();
		}
		return columnNames;
	}
	/**
	 * check all columns are not empty.
	 * @return boolean : return false if one or more is empty, or not true.
	 * @exception InvalidNullableException, DBTypeException
	 */
	@Override
	public boolean isValid() throws InvalidNullableException, DBTypeException{
		Field[] columns = type.getDeclaredFields();
		InvalidNullableException exception=null;
		for ( int i=0; i < columns.length; i++) {
			String ColumnName=columns[i].getName();
			String getMethodName="get" + ColumnName.toUpperCase().charAt(0) + ColumnName.substring(1);
			try {
				String Res=(String) type.getMethod(getMethodName).invoke(this);
				if ( Res.length() <= 0 )
				{
					if ( exception == null)
						exception = new InvalidNullableException();
					exception.addFieldError(ColumnName, "NULL");
				}
			} catch (Exception e) {
				throw new DBTypeException(type.getSimpleName(), e.getMessage());
			}
		}
		
		if ( exception != null)
			throw exception;
		return true;
		
	}
	
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null ) return false;
		if ( o.getClass() != this.getClass() ) return false;
		return true;
	}
	
	@Override
	public int hashCode() throws DBTypeException{
		Field[] columns = type.getDeclaredFields();
		String[] columnValues= new String[columns.length];
		for ( int i=0; i < columns.length; i++) {
			String ColumnName=columns[i].getName();
			String getMethodName="get" + ColumnName.toUpperCase().charAt(0) + ColumnName.substring(1);
			try {
				columnValues[i]=(String) type.getMethod(getMethodName).invoke(this);
			} catch (Exception e) {
				throw new DBTypeException(type.getSimpleName(), e.getMessage());
			}
		}
		return Arrays.hashCode(columnValues);
	}
}
