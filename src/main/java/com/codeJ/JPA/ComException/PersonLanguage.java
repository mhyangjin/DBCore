package com.codeJ.JPA.ComException;

import lombok.Getter;
import lombok.Setter;

/**
 * This is to specify the language used for each person.
 * However, it is not recommended.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 *///@Component
//@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value=WebApplicationContext.SCOPE_SESSION)
@Getter
@Setter
public class PersonLanguage {
	private NationCode nationCode;
	private String PersonId;
	
	//default value=KR
	public PersonLanguage() {
		nationCode=NationCode.KR;
		PersonId="1";
	}
}
