package kr.co.codeJ.JPA.ComException;

import java.util.HashMap;
import java.util.Map;

/** This is ErrorMessages for language Set
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
public class ErrorMessages {
	Map<String, String> messages;
	/**
	 * constructor
	 */
	public ErrorMessages()
	{
		messages=new HashMap<>();
	}
	/**
	 * put message set
	 * @param String code
	 * @param String message
	 * @return ErrorMessages
	 */
	public ErrorMessages put(String code, String message) {
		messages.put(code, message);
		return this;
	}
	
	/**
	 * get message string
	 * @param String code
	 * @return message value
	 */
	public String getMessage(String code) {
		return messages.get(code);
	}
}
