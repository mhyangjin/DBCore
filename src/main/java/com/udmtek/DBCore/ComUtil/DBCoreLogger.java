package com.udmtek.DBCore.ComUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.udmtek.DBCore.ComException.DBException;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;


/** 
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public class DBCoreLogger {

	private static Logger logger=LoggerFactory.getLogger(DBCoreLogger.class);
	/**
	 * print trace message.
	 */
	public static void printTrace( String msg) {
		logger.trace(msg);
	}
	/**
	 * print trace message.
	 */
	public static void printTrace(Object object, String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.trace(msg);
	}
	/**
	 * print debug message.
	 */
	public static void printDebug(Object object, String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.debug(msg);
	}
	/**
	 * print debug message.
	 */
	public static void printDebug(String msg) {
		logger.debug(msg);
	}
	/**
	 * print info message.
	 */
	public static void printInfo(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.info(msg);
	}
	/**
	 * print info message.
	 */
	public static void printInfo(String msg) {
		logger.info(msg);
	}

	/**
	 * print warning message.
	 */
	public static void printWarn(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.warn(msg);
	}
	/**
	 * print warning message.
	 */
	public static void printWarn(String msg) {
		logger.warn(msg);
	}
	/**
	 * print error message
	 */
	public static void printError(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.error(msg);
	}
	/**
	 * print error message
	 */
	public static void printError(String msg) {
		logger.error(msg);
	}
	/**
	 * print trace message from DBexception
	 */
	public static void printTrace(Object object,DBException e) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.trace(e.getMessages());
	}
	/**
	 * print debug message from DBexception
	 */
	public static void printDebug(Object object,DBException e) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.debug(e.getMessages());
	}
	/**
	 * print info message from DBexception
	 */
	public static void printInfo(Object object,DBException e) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.info(e.getMessages());
	}
	/**
	 * print warning message from DBexception
	 */
	public static void printWarn(Object object,DBException e) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.warn(e.getMessages());
	}
	/**
	 * print error message from DBexception
	 */
	public static void printError(Object object,DBException e) {
		logger=LoggerFactory.getLogger(object.getClass());
		logger.error(e.getMessages());
	}
	/**
	 * print trace message with Database info.
	 */
	public static void printDBTrace(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		
		logger.trace(dbInfomsg+msg);
	}
	/**
	 * print debug message with Database info.
	 */
	public static void printDBDebug(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		
		logger.debug(dbInfomsg+msg);
	}
	/**
	 * print info message with Database info.
	 */
	public static void printDBInfo(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.info(dbInfomsg+msg);
	}
	/**
	 * print warning message with Database info.
	 */
	public static void printDBWarn(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.warn(dbInfomsg+msg);
	}
	/**
	 * print error message with Database info.
	 */
	public static void printDBError(Object object,String msg) {
		logger=LoggerFactory.getLogger(object.getClass());
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.error(dbInfomsg+msg);
	}
	/**
	 * print trace message from DBexception
	 */
	public static void printTrace(DBException e) {
		logger.trace(e.getMessage());
	}
	/**
	 * print debug message from DBexception
	 */
	public static void printDebug(DBException e) {
		logger.debug(e.getMessage());
	}
	/**
	 * print info message from DBexception
	 */
	public static void printInfo(DBException e) {
		logger.info(e.getMessage());
	}
	/**
	 * print warning message from DBexception
	 */
	public static void printWarn(DBException e) {
		logger.warn(e.getMessage());
	}
	/**
	 * print error message from DBexception
	 */
	public static void printError(DBException e) {
		logger.error(e.getMessage());
	}
	/**
	 * print trace message with Database info.
	 */
	public static void printDBTrace(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		
		logger.trace(dbInfomsg+msg);
	}
	/**
	 * print debug message with Database info.
	 */
	public static void printDBDebug(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		
		logger.debug(dbInfomsg+msg);
	}
	/**
	 * print info message with Database info.
	 */
	public static void printDBInfo(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.info(dbInfomsg+msg);
	}
	/**
	 * print warning message with Database info.
	 */
	public static void printDBWarn(String msg) {
		String dbInfomsg="";
		DBCoreSession thisSession=DBCoreSessionManager.getCurrentSession();
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
				
		logger.warn(dbInfomsg+msg);
	}
	/**
	 * print error message with Database info.
	 */
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
