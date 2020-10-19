package kr.co.codeJ.JPA.ComException;

import lombok.Getter;

/**
 * This is FieldError Using ErrorResponse
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
@Getter
public class FieldError {
	String field;
	String value;
	
	/**
	 * constructor
	 * @param String field
	 * @param String value
	 */
	public FieldError(String field, String value) {
		this.field = field;
		this.value = value;
	}
	
	/**
	 * return error String
	 *@return String
	 */
	public String toString() {
		return field + ":" + value;
	}
}
