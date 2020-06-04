package com.udmtek.DBCore.DBAccessor;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.udmtek.DBCore.DAOModel.GenericDAOImpl;


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
	public void readyConnect(SessionFactory argFactory, String argSessionName);
	
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
	public Session getThisSession();
	/**
	 * end the transaction
	 * @param CommitOK (true:commit,false:rollback)
	 * @return return true if success otherwise false.
	 */
	public boolean endTransaction(boolean CommitOK);
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
	
	/**
	 * get the EntityManager
	 * @param
	 * @return EntityManager
	 */
	public EntityManager getEntityManager();
	
	/**
	 * get DAOImpl of T
	 * @param
	 * @return T
	 */
	public <T> T getDAOImpl(Class<T> type);
	
	/**
	 * get DAOImpl using TableName
	 * @param
	 * @return GenericDAOImpl<?>
	 */
	public GenericDAOImpl<?> getDAOImpl(String TableName);


}
