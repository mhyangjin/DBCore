package kr.co.codeJ.JPA.ComException;

import lombok.Getter;

/**
 * This is DBAccessException . ErrorCode.DB_TYPE_ERROR
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class DBTypeException extends DBException {

	private ErrorCode errorCode;
	private String reason;
	
	/**
	 * constructor DBTypeException
	 * ErrorCode: ErrorCode.DB_TYPE_ERROR
	 */
	public DBTypeException() {
		super("");
		this.errorCode = ErrorCode.DB_TYPE_ERROR;
	}
	/**
	 * constructor DBTypeException
	 * @param message String
	 */
	public DBTypeException(String message) {
		super(message);
		this.errorCode = ErrorCode.DB_TYPE_ERROR;
	}
	/**
	 * constructor DBTypeException
	 * @param String reason
	 * @param String message
	 */
	public DBTypeException( String reason, String message) {
		super(message);
		this.reason = reason;
		this.errorCode = ErrorCode.DB_TYPE_ERROR;
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
