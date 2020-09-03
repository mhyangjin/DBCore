package com.codeJ.JPA.DAOModel;

import java.io.Serializable;

/**
 * This is Interface for the composite key class
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface GenericEntityKey extends Serializable {
	/**
	 * get column names 
	 * @return String[]
	 */
	public String[] getKeyColumns();
	/**
	 * check all columns are not empty.
	 * @return boolean : return false if one or more is empty, or not true.
	 */
	public boolean isValid();

}
