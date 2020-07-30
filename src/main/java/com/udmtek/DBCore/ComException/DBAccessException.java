package com.udmtek.DBCore.ComException;

import lombok.Getter;

/** This is DBAccessException . ErrorCode.DB_ACCESS_ERROR
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class DBAccessException extends DBException {
	private ErrorCode errorCode;
	private String reason;
	
	/**
	 * constructor DBAccessException
	 * ErrorCode: ErrorCode.DB_ACCESS_ERROR
	 */
	public DBAccessException() {
		super("");
		this.errorCode = ErrorCode.DB_ACCESS_ERROR;
	}
	/**
	 * constructor DBAccessException
	 * @param message String
	 */
	public DBAccessException(String message) {
		super(message);
		this.errorCode = ErrorCode.DB_ACCESS_ERROR;
	}
	/**
	 * constructor DBAccessException
	 * @param String reason
	 * @param String message
	 */
	public DBAccessException( String reason, String message) {
		super(message);
		this.reason = reason;
		this.errorCode = ErrorCode.DB_ACCESS_ERROR;
	}
	/**
	 * return error messages
	 * @return String
	 */
	@Override
	public String getMessages() {
		return errorCode.toString() + ":" + reason + ":" + super.getMessage();
	}
}
