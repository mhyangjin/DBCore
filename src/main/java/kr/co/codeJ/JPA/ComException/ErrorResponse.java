package kr.co.codeJ.JPA.ComException;

import java.util.List;
import lombok.Getter;

/**
 * This is ErrorResponse for DBExceptionHandler
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class ErrorResponse {
	private String message;
	private String code;
	private List<FieldError> errors;

	/**
	 * constructor for DBAccessException
	 * @param DBAccessException ex
	 */
	public ErrorResponse( DBAccessException ex) {
		this.code =  ex.getErrorCode().getCode();
	}
	/**
	 * constructor for DBTypeException
	 * @param DBTypeException ex
	 */
	public ErrorResponse( DBTypeException ex) {
		this.code =  ex.getErrorCode().getCode();
	}
	/**
	 * constructor for FieldException
	 * @param FieldException ex
	 */
	public ErrorResponse( FieldException ex) {
		this.code =  ex.getErrorCode().getCode();
		this.errors = ex.getFieldErrors();
	}
	/**
	 * set MessageString
	 * @param String ErrorMessages
	 * @return ErrorResponse
	 */
	public ErrorResponse setMessage(String ErrorMessages) {
		this.message =ErrorMessages;
		return this;
	}

}
