package kr.co.codeJ.JPA.ComException;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/** This is InvalidArgException . ErrorCode.INVALID_ARGUMENT
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class InvalidArgException extends FieldException {
	
	private ErrorCode errorCode;
	private List<FieldError> fieldErrors;
	
	/**
	 * constructor InvalidArgException
	 * ErrorCode: ErrorCode.INVALID_ARGUMENT
	 */
	public InvalidArgException() {
		super("");
		this.errorCode = ErrorCode.INVALID_ARGUMENT;
		fieldErrors= new ArrayList<>();
	}
	/**
	 * constructor InvalidArgException
	 * @param String field
	 * @param String value
	 */
	public InvalidArgException(String field, String value) {
		super("");
		this.errorCode = ErrorCode.INVALID_ARGUMENT;
		fieldErrors= new ArrayList<>();
		fieldErrors.add( new FieldError(field, value));
	}
	/**
	 * constructor InvalidArgException
	 * @param List<FieldError> fieldErrors
	 */
	public InvalidArgException(List<FieldError> fieldErrors) {
		super("");
		this.errorCode = ErrorCode.INVALID_ARGUMENT;
		this.fieldErrors= fieldErrors;
	}
	
	@Override
	public InvalidArgException addFieldError(String field, String value) {
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
