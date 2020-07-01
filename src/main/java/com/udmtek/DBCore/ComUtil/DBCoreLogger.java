package com.udmtek.DBCore.ComUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;


/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public class DBCoreLogger {
	private static Logger logger=LoggerFactory.getLogger(DBCoreLogger.class);
	public static void printTrace(String msg) {
		logger.trace(msg);
	}
	
	public static void printDebug(String msg) {
		logger.debug(msg);
	}
		/**
	 * print info.
	 */
	public static void printInfo(String msg) {
		logger.info(msg);
	}

	public static void printWarn(String msg) {
		logger.warn(msg);
	}
	/**
	 *print error
	 */
	public static void printError(String msg) {
		logger.error(msg);
	}

	public static void printDBTrace(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		
		logger.trace(dbInfomsg+msg);
	}
	

	public static void printDBDebug(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		
		logger.debug(dbInfomsg+msg);
	}

	public static void printDBInfo(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.info(dbInfomsg+msg);
	}
	
	public static void printDBWarn(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.warn(dbInfomsg+msg);
	}
	
	public static void printDBError(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.error(dbInfomsg+msg);
	}
}
