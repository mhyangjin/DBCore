package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface EntityKey extends Serializable {
	/**
	 * get column names 
	 * @param
	 * @return String[]
	 */
	public String[] getKeyColumns();
	/**
	 * check all columns are not empty.
	 * @param
	 * @return boolean : return false if one or more is empty, or not true.
	 */
	public boolean isValid();

}
