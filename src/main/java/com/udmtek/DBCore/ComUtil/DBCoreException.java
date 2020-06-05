/**
 * 
 */
package com.udmtek.DBCore.ComUtil;

import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public class DBCoreException extends Exception {

	private static final long serialVersionUID = 1L;
	private String SessionInfo;
	private String message;
	
	public DBCoreException( ) {
		super("DBCoreException");
		DBCoreSession currSession=DBCoreSessionManager.DBSession.get();
		if (currSession == null)
			SessionInfo="NotInSession";
		else
			SessionInfo=currSession.getTransactionID();
	}
	
	public DBCoreException(String message) {
		DBCoreSession currSession=DBCoreSessionManager.DBSession.get();
		if (currSession == null)
			SessionInfo="NotInSession";
		else
			SessionInfo=currSession.getTransactionID();
		this.message=message;
	}
	public String getMessage() {
		return this.message;
	}
	
	public String getSessionInfo() {
		return SessionInfo;
	}
	
	public void print() {
		DBCoreLogger.printError(SessionInfo + ":" + message);
	}
}
