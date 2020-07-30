package com.udmtek.DBCore.ComException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/**
 * This is ExceptionHandler for DBExceptions
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@ControllerAdvice
public class DBExceptionHandler {
	@Autowired
	@Qualifier("personLanguage")
	PersonLanguage personLanguage;
	
	@Autowired
	@Qualifier("nationErrorMessages")
	NationErrorMessages nationErrorMessages;
	
	/**
	 * ExceptionHandler for DBAccessException
	 * @param DBAccessException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(DBAccessException.class)
	public ResponseEntity<ErrorResponse> handleDBAccessException( DBAccessException e) {
		DBCoreLogger.printTrace("DBAccessException catched!!!" + e.getMessages());
		ErrorResponse response=new ErrorResponse(e);
		String errMessage=nationErrorMessages.getMessages(personLanguage.getNationCode(),e.getErrorCode() );
		return new ResponseEntity<> (response.setMessage(errMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * ExceptionHandler for InvalidArgException
	 * @param InvalidArgException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InvalidArgException.class)
	public ResponseEntity<ErrorResponse> handleInvalidArgException( InvalidArgException e) {
		DBCoreLogger.printTrace("InvalidArgException catched!!!"+ e.getMessages());
		ErrorResponse response=new ErrorResponse(e);
		String errMessage=nationErrorMessages.getMessages(personLanguage.getNationCode(),e.getErrorCode() );
		return new ResponseEntity<> (response.setMessage(errMessage), HttpStatus.BAD_REQUEST);
	}
	/**
	 * ExceptionHandler for InvalidLengthException
	 * @param InvalidLengthException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InvalidLengthException.class)
	public ResponseEntity<ErrorResponse> handleInvalidLengthException( InvalidLengthException e) {
		DBCoreLogger.printTrace("InvalidLengthException catched!!!"+ e.getMessages());
		ErrorResponse response=new ErrorResponse(e);
		String errMessage=nationErrorMessages.getMessages(personLanguage.getNationCode(),e.getErrorCode() );
		return new ResponseEntity<> (response.setMessage(errMessage), HttpStatus.BAD_REQUEST);
	}
	/**
	 * ExceptionHandler for InvalidNullableException
	 * @param InvalidNullableException
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InvalidNullableException.class)
	public ResponseEntity<ErrorResponse> handleInvalidNullableException( InvalidNullableException e) {
		DBCoreLogger.printTrace("InvalidNullableException catched!!!"+ e.getMessages());
		ErrorResponse response=new ErrorResponse(e);
		String errMessage=nationErrorMessages.getMessages(personLanguage.getNationCode(),e.getErrorCode() );
		return new ResponseEntity<> (response.setMessage(errMessage), HttpStatus.BAD_REQUEST);
	}
	/**
	 * ExceptionHandler for DBTypeException
	 * @param DBTypeException
	 * @return ResponseEntity
	 */	
	@ExceptionHandler(DBTypeException.class)
	public ResponseEntity<ErrorResponse> handleDBTypeException( DBTypeException e) {
		DBCoreLogger.printTrace("DBTypeException catched!!!"+ e.getMessages());
		ErrorResponse response=new ErrorResponse(e);
		String errMessage=nationErrorMessages.getMessages(personLanguage.getNationCode(),e.getErrorCode() );
		return new ResponseEntity<> (response.setMessage(errMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
