package com.udmtek.DBCore.ComException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This is ExceptionHandler for DBExceptions
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@ControllerAdvice
@DependsOn({"nationErrorMessages","personLanguage"})
public class DBExceptionHandler {
	private static Logger logger=LoggerFactory.getLogger(DBExceptionHandler.class);
	
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
		logger.trace("DBAccessException catched!!! {}",e.getMessages());
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
		logger.trace("InvalidArgException catched!!! {}",e.getMessages());
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
		logger.trace("InvalidLengthException catched!!! {}",e.getMessages());
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
		logger.trace("InvalidNullableException catched!!! {}",e.getMessages());
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
		logger.trace("DBTypeException catched!!! {}",e.getMessages());
		ErrorResponse response=new ErrorResponse(e);
		String errMessage=nationErrorMessages.getMessages(personLanguage.getNationCode(),e.getErrorCode() );
		return new ResponseEntity<> (response.setMessage(errMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
