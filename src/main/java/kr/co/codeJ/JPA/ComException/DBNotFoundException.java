package kr.co.codeJ.JPA.ComException;

public class DBNotFoundException extends DBException {
	private ErrorCode errorCode;
	private String reason;
	
	/**
	 * constructor DBAccessException
	 * ErrorCode: ErrorCode.DB_NOT_FOUND
	 */
	public DBNotFoundException() {
		super("");
		this.errorCode = ErrorCode.DB_NOT_FOUND;
	}
	/**
	 * constructor DBAccessException
	 * @param message String
	 */
	public DBNotFoundException(String message) {
		super(message);
		this.errorCode = ErrorCode.DB_NOT_FOUND;
	}
	/**
	 * constructor DBAccessException
	 * @param String reason
	 * @param String message
	 */
	public DBNotFoundException( String reason, String message) {
		super(message);
		this.reason = reason;
		this.errorCode = ErrorCode.DB_NOT_FOUND;
	}
	/**
	 * return error messages
	 * @return String
	 */
	@Override
	public String getMessages() {
		return errorCode.toString() + ":" + reason + ":" + super.getMessage();
	}
	
	@Override
	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
