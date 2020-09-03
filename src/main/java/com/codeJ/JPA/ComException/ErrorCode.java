package com.codeJ.JPA.ComException;

import lombok.Getter;

/** This is enum for ErrorCode
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public enum ErrorCode {
	DB_ACCESS_ERROR("D001"),	//DBAccessException
	INVALID_ARGUMENT("D002"),	//InvalidArgException
	INVALID_LENGTH("D003"),		//InvalidLengthException
	INVALID_NULLABLE("D004"),	//InvalidNallableException
	DB_TYPE_ERROR("D005"),		//DBtypeException
	DB_DUPLICATE("D006"),		//Duplicate
	DB_NOT_FOUND("D007")		//Not Found
	;

	private final String code;
	
	ErrorCode(final String code) {
		this.code = code;
	}
}
