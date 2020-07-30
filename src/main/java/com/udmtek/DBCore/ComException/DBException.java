package com.udmtek.DBCore.ComException;

/** This is super class for DBExceptions
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
public abstract class DBException extends RuntimeException {
	/**
	 * make errorMesaages
	 * @return String
	 */
	public abstract String getMessages();
	/**
	 * return ErrorCode
	 * @return ErrorCode
	 */
	public abstract ErrorCode getErrorCode();
	/**
	 * constructor
	 */
	public DBException( String message) {
		super(message);
	}
}
