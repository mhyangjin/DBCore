package com.udmtek.DBCore.DBAccessor;

import org.hibernate.SessionFactory;

/**
 * management DBCoreSession set.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.0.1
 */
public interface DBCoreSessionManager{
	
	public void startSessionManager(String argPersistUnit);
	public String getPersistUnit();
	/**
	 * print persistence UnitName and maximumPoolSize 
	 */	
	public void printValues();
	/**
	 * get the unused DBCoreSesion.
	 * @return DBCoreSession
	 */
	public DBCoreSession openSession();
	/**
	 * get the unused DBCoreSesion.
	 * @param int retryNo has to larger than 0
	 * @param int waitTime mili-seconds 
	 * @return DBCoreSession
	 */	
	public DBCoreSession openSession(int retryNo, int waitTime);
	
	public boolean closeSession(DBCoreSession currSession);
	
	public SessionFactory getSessionFactory();
	
	public void coloseAllSessions();
	
}