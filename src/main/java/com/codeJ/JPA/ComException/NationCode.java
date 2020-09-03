package com.codeJ.JPA.ComException;

/** This is enum of nation for foreign Language 
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
public enum NationCode {
	KR("ko-KR"),
	CN("zh-CN"),
	EN("en_US"),
	;
	private String nationString;
	
	NationCode(String nationString) {
		this.nationString = nationString;
	}
	public String getNationString() {
		return nationString;
	}
}
