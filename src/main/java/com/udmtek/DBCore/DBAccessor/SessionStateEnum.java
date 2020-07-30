package com.udmtek.DBCore.DBAccessor;

import java.util.Arrays;
import java.util.List;

/**
 * enum of Session State
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.3.0
  */
public enum SessionStateEnum {
	INIT("InitState",Arrays.asList("openSession")),
	OPEN("OpenState",Arrays.asList("beginTransaction","closeSession")),
	BEGIN("BeginState",Arrays.asList("endTransaction")),
	END("EndState",Arrays.asList("beginTransaction","closeSession")),
	;
	//Session State String
	private String SessionState;
	//This is specification that can be changed next state.
	private List<String> methods;
	
	SessionStateEnum(String SessionState, List<String> methods) {
		this.SessionState = SessionState;
		this.methods = methods;
	}
	//check to possibility changing next state.
	public boolean isPossibleProcess(String Next) {
		return (methods.contains(Next));
	}
}