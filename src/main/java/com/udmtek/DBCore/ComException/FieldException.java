package com.udmtek.DBCore.ComException;

import java.util.List;

/** This is super class for Validation Check
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
public abstract class FieldException extends DBException {
	/**
	 * get list of FieldError
	 * @return List<FieldError> 
	 */
	public abstract List<FieldError> getFieldErrors();
	/**
	 * add to list of FieldError
	 * @param String field
	 * @param String value
	 * @return this 
	 */
	public abstract FieldException addFieldError(String field, String value);
	/**
	 * constructor
	 * @param message
	 */
	public FieldException( String message) {
		super(message);
	}
}
