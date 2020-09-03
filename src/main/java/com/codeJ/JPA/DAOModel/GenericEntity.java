package com.codeJ.JPA.DAOModel;

/**
 * This is Interface for Entity class
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface GenericEntity extends Cloneable{
	/**
	 * make string from fields
	 * @return String
	 */
	public String ToString();
	
	/**
	 * return key column 
	 * @return boolean
	 */
	
	public GenericEntityKey getKey();
}
