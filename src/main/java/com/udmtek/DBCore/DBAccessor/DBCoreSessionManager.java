package com.udmtek.DBCore.DBAccessor;

import org.hibernate.SessionFactory;

/**
 * This is for management DBCoreSession set.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.0.1
 */
public interface DBCoreSessionManager{
	/**
	 * print persistence UnitName and maximumPoolSize 
	 */	
	public void printValues();
	/**
	 * get the unused DBCoreSesion.
	 * @return DBCoreSession
	 */
	public DBCoreSession getSession();
	/**
	 * get the unused DBCoreSesion.
	 * @param int retryNo has to larger than 0
	 * @param int waitTime mili-seconds 
	 * @return DBCoreSession
	 */	
	public DBCoreSession getSession(int retryNo, int waitTime);
}