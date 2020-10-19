package kr.co.codeJ.JPA.ComException;

public class DBDuplicateException extends DBException {
	private ErrorCode errorCode;
	private String reason;
	
	/**
	 * constructor DBAccessException
	 * ErrorCode: ErrorCode.DB_DUPLICATE
	 */
	public DBDuplicateException() {
		super("");
		this.errorCode = ErrorCode.DB_DUPLICATE;
	}
	/**
	 * constructor DBAccessException
	 * @param message String
	 */
	public DBDuplicateException(String message) {
		super(message);
		this.errorCode = ErrorCode.DB_DUPLICATE;
	}
	/**
	 * constructor DBAccessException
	 * @param String reason
	 * @param String message
	 */
	public DBDuplicateException( String reason, String message) {
		super(message);
		this.reason = reason;
		this.errorCode = ErrorCode.DB_DUPLICATE;
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
