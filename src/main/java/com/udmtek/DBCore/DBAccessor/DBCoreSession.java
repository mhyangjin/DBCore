package com.udmtek.DBCore.DBAccessor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * interface DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version 0.3.0
 */
public interface DBCoreSession{
	/**
	 * set Session Factory and Session Name in Empty DBCoreSession
	 * @param SessionFactory, String 
	 * @return void
	 */
	public void readyConnect(SessionFactory sessionFactory, String argSessionName);
	
	/**
	 * get Session and create an EntityManager
	 * @return SessionStateEnum
	 */
	public SessionStateEnum openSession();
	/**
	 * return session, close EntityManager
	 * @return SessionStateEnum
	 */
	public SessionStateEnum closeSession();
	/**
	 * begin transaction
	 * @param ReadOnly (true:read, false:writable)
	 * @return SessionStateEnum
	 */
	public SessionStateEnum beginTransaction(boolean ReadOnly);
	
	/**
	 * get Session of current Session
	 * @return Session
	 */
	public Session getThisSession();
	/**
	 * end the transaction
	 * @param CommitOK (true:commit,false:rollback)
	 * @return return true if success otherwise false.
	 */
	public SessionStateEnum endTransaction(boolean CommitOK);
	/**
	 * get the transactionID
	 * @return add sequential number to given Session name from DBCoreSessionManager 
	 */
	public String getTransactionID();
	
	/**
	 * get the sessionFactory
	 * @return sessionFactory
	 */
	public SessionFactory getSessionFactory();

	/**
	 * get current Session State
	 * @return SessionStateEnum
	 */
	public SessionStateEnum getSessionState();
}

