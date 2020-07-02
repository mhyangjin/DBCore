package com.udmtek.DBCore.DBAccessor;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;


/**
 * management DBCoreSession set.
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
public interface DBCoreSessionManager{
	/**
	 * This is for creating Thread local Variable.
	 * It will be can get DBCoreSession in current thread.
	 */
	public static ThreadLocal<DBCoreSession> DBSession=new ThreadLocal<DBCoreSession>();	
<<<<<<< HEAD
=======
	/**
	 * get opended session in current thread.
	 * @param
	 * @return DBCoreSession
	 */
	public static DBCoreSession getCurrentSession() { return DBSession.get();} 

>>>>>>> udmtek
	/**
	 * make connection with specified Database
	 * @param String  : persistence-unit name in peristence.xml
	 * @return void
	 */
	public void startSessionManager(String argPersistUnit);
	/**
	 * get persistence-unit name of connected database
	 * @param
	 * @return String
	 */
	public String getPersistUnit();
	/**
	 * print persistence UnitName and maximumPoolSize 
	 * @param
	 * @return void
	 */
	public void printValues();
	/**
	 * get the unused DBCoreSesion. return null if there is no unused session immediately.
	 * @return DBCoreSession
	 */
	public DBCoreSession openSession();
	/**
	 * get the unused DBCoreSesion.
	 * @param int retryNo has to larger than 0
	 * @param int waitTime milli-seconds 
	 * @return DBCoreSession
	 */	
	public DBCoreSession openSession(int retryNo, long waitTime);
	
	/**
	 * get opended session in current thread.
	 * @param
	 * @return DBCoreSession
	 */
	public DBCoreSession getCurrentSession();
	
	/**
	 * close session.
	 * @param opended session.
	 * @return boolean
	 */
	public boolean closeSession(DBCoreSession currSession);
	
	/**
	 * get SessionFactory.
	 * @param
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory();
	
	/**
	 * close all sessions. can't use DBCoreSessionManager any more.
	 * @param
	 * @return void
	 */
	public void coloseAllSessions();
	
}