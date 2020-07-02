package com.udmtek.DBCore.DBAccessor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * interface DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
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
	 * @param 
	 * @return boolean : return true if success otherwise false.
	 */
	public boolean openSession();
	/**
	 * return session, close EntityManager
	 * @param 
	 * @return return true if success otherwise false.
	 */
	public boolean closeSession();
	/**
	 * begin transaction
	 * @param ReadOnly (true:read, false:writable)
	 * @return return true if success otherwise false.
	 */
	public boolean beginTransaction(boolean ReadOnly);
	
<<<<<<< HEAD
=======
	public Session getThisSession();
>>>>>>> udmtek
	/**
	 * end the transaction
	 * @param CommitOK (true:commit,false:rollback)
	 * @return return true if success otherwise false.
	 */
	public boolean endTransaction(boolean CommitOK);

	/**
	 * get state of the current session
	 * @param
	 * @return State
	 */
	public State getSessionState();
	
	/**
	 * get this session is open state or not.
	 * @param
	 * @return boolean return true if this session is open otherwise false
	 */
	public boolean isOpened();
	
	/**
	 * get this session is begin transaction state or not
	 * @param
	 * @return boolean return true if already begin transaction otherwise false
	 */
	public boolean isbeginedTransacion();
	
	/**
	 * get the transactionID
	 * @param 
	 * @return add sequential number to given Session name from DBCoreSessionManager 
	 */
	public String getTransactionID();
	
	/**
	 * get the sessionFactory
	 * @param 
	 * @return sessionFactory
	 */
	public SessionFactory getSessionFactory();

	public boolean isBeginTransaction();
}
