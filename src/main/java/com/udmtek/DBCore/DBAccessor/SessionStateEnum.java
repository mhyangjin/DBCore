package com.udmtek.DBCore.DBAccessor;

import java.util.Arrays;
import java.util.List;

public enum SessionStateEnum {
	INIT("InitState",Arrays.asList("openSession")),
	OPEN("OpenState",Arrays.asList("beginTransaction","closeSession")),
	BEGIN("BeginState",Arrays.asList("endTransaction")),
	END("EndState",Arrays.asList("beginTransaction","closeSession")),
	;
	
	private String SessionState;
	private List<String> methods;
	
	SessionStateEnum(String SessionState, List<String> methods) {
		this.SessionState = SessionState;
		this.methods = methods;
	}
	
	public boolean isPossibleProcess(String Next) {
		return (methods.contains(Next));
	}
}