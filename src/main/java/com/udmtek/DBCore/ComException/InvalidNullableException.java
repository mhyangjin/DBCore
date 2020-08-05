package com.udmtek.DBCore.ComException;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/** This is InvalidNullableException . ErrorCode.INVALID_NULLABLE
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class InvalidNullableException extends FieldException  {
	private ErrorCode errorCode;
	private List<FieldError> fieldErrors;
	
	/**
	 * constructor InvalidNullableException
	 * ErrorCode: ErrorCode.INVALID_NULLABLE
	 */
	public InvalidNullableException() {
		super("");
		this.errorCode = ErrorCode.INVALID_NULLABLE ;
		fieldErrors= new ArrayList<>();
	}
	/**
	 * constructor InvalidNullableException
	 * @param String field
	 * @param String value
	 */
	public InvalidNullableException(String field, String value) {
		super("");
		this.errorCode = ErrorCode.INVALID_NULLABLE;
		fieldErrors= new ArrayList<>();
		fieldErrors.add( new FieldError(field, value));
	}
	/**
	 * constructor InvalidNullableException
	 * @param List<FieldError> fieldErrors
	 */
	public InvalidNullableException(List<FieldError> fieldErrors) {
		super("");
		this.errorCode = ErrorCode.INVALID_NULLABLE;
		this.fieldErrors= fieldErrors;
	}
	@Override
	public InvalidNullableException addFieldError(String field, String value) {
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
