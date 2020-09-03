package com.codeJ.JPA.DBAccessor;

import org.hibernate.SessionFactory;


/**
 * management DBSession set.
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
public interface DBSessionManager{
	/**
	 * This is for creating Thread local Variable.
	 * It will be can get DBCoreSession in current thread.
	 */
	public static ThreadLocal<DBSession> DBSession=new ThreadLocal<DBSession>();	
	/**
	 * get opended session in current thread.
	 * @return DBSession
	 */
	public static DBSession getCurrentSession() { return DBSession.get();} 

	public static String getSessionInfo() {
		DBSession thisSession=getCurrentSession();
		String dbInfomsg="";
		if ( thisSession == null )
			dbInfomsg="[Session:Null]";
		else 
			dbInfomsg="[Session:" + thisSession.getTransactionID()+"]";
		return dbInfomsg;
	}
	/**
	 * make connection with specified Database
	 * @param String  : persistence-unit name in peristence.xml
	 */
	public void startSessionManager(String argPersistUnit);
	/**
	 * get persistence-unit name of connected database
	 * @return String
	 */
	public String getPersistUnit();
	/**
	 * print persistence UnitName and maximumPoolSize 
	 */
	public void printValues();
	/**
	 * get the unused DBCoreSesion. return null if there is no unused session immediately.
	 * @return DBCoreSession
	 */
	public DBSession openSession();
	/**
	 * get the unused DBCoreSesion.
	 * @param int retryNo has to larger than 0
	 * @param int waitTime milli-seconds 
	 * @return DBCoreSession
	 */	
	public DBSession openSession(int retryNo, long waitTime);
	/**
	 * close session.
	 * @param opended session.
	 * @return SessionStateEnum
	 */
	public SessionStateEnum closeSession(DBSession currSession);
	/**
	 * get SessionFactory.
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory();
	
	/**
	 * close all sessions. can't use DBCoreSessionManager any more.
	 */
	public void closeAllSessions();
	
}