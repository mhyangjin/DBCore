package com.udmtek.DBCore.ComException;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/** This is InvalidLengthException . ErrorCode.INVALID_LENGTH
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class InvalidLengthException extends FieldException {
	private ErrorCode errorCode;
	private List<FieldError> fieldErrors;
	
	/**
	 * constructor InvalidLengthException
	 * ErrorCode: ErrorCode.INVALID_LENGTH
	 */
	public InvalidLengthException() {
		super("");
		this.errorCode = ErrorCode.INVALID_LENGTH;
		fieldErrors= new ArrayList<>();
	}
	/**
	 * constructor InvalidLengthException
	 * @param String field
	 * @param String value
	 */
	public InvalidLengthException(String field, String value) {
		super("");
		this.errorCode = ErrorCode.INVALID_LENGTH;
		fieldErrors= new ArrayList<>();
		fieldErrors.add( new FieldError(field, value));
	}
	/**
	 * constructor InvalidLengthException
	 * @param List<FieldError> fieldErrors
	 */
	public InvalidLengthException(List<FieldError> fieldErrors) {
		super("");
		this.errorCode = ErrorCode.INVALID_LENGTH;
		this.fieldErrors= fieldErrors;
	}
	@Override
	public InvalidLengthException addFieldError(String field, String value) {
		fieldErrors.add( new FieldError(field, value));
		return this;
	}
	
	@Override
	public String getMessages() {
		String errorFileds ="";
		for ( FieldError fieldError:fieldErrors) errorFileds = errorFileds + "," + fieldError.toString();
		return errorCode.toString() + ":" + errorFileds + ":" + super.getMessage();
	}
}
