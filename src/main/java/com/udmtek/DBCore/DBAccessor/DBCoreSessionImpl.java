package com.udmtek.DBCore.DBAccessor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.udmtek.DBCore.ComException.DBAccessException;

/** Implementation of the DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
@Component("DBCoreSession")
@Scope(value = "prototype")
public class DBCoreSessionImpl implements DBCoreSession {
	private static Logger logger=LoggerFactory.getLogger(DBCoreSessionImpl.class);
				
	@Autowired
	ApplicationContext context;
	
	private SessionFactory sessionFactory=null;
	private String SessionName="";
	SessionStateEnum sessionState;	

	private Session thisSession=null;
	private int SessionSeq=0;
	
	private int TransactionSeq=0;
	private boolean ReadOnly;
	
	/**
	 * set Session Factory and Session Name in Empty DBCoreSession
	 * @param SessionFactory, String 
	 * @return void
	 */
	@Override
	public void readyConnect(SessionFactory sessionFactory, String argSessionName) {
		this.sessionFactory=sessionFactory;
		SessionName=argSessionName;
		sessionState=SessionStateEnum.INIT;
	}
	/**
	 * get Session and create an EntityManager
	 * @return SessionStateEnum
	 * @exception DBAccessException
	 */
	@Override
	public SessionStateEnum openSession() throws DBAccessException {
		if ( !sessionState.isPossibleProcess("openSession")) {
			throw new DBAccessException(sessionState.toString(),"["+ getTransactionID() + "] can not openSession");
		}
		
		thisSession=sessionFactory.openSession();
		if (SessionSeq >= 2147483647 ) {
			SessionSeq=1;
		}
		SessionSeq++;
		sessionState=SessionStateEnum.OPEN;
		return sessionState;
	}
	/**
	 * return session, close EntityManager
	 * @return SessionStateEnum
	 * @exception DBAccessException
	 */
	@Override
	public SessionStateEnum closeSession() throws DBAccessException {
		if ( !sessionState.isPossibleProcess("closeSession"))
			throw new DBAccessException(sessionState.toString(),"["+ getTransactionID() + "] can not closeSession");
		
		thisSession.close();
		thisSession=null;
		sessionState=SessionStateEnum.INIT;
		return sessionState;
	}
	/**
	 * get Session of current Session
	 * @return Session
	 */
	@Override
	public Session getThisSession() {
		return thisSession;
	}
	/**
	 * get the transactionID
	 * @return add sequential number to given Session name from DBCoreSessionManager 
	 */
	@Override
	public String getTransactionID() {
		String TransactionID=SessionName;
		switch (sessionState) {
		case INIT:
			TransactionID = TransactionID + "..";
			break;
		case OPEN:
			TransactionID = TransactionID +"."+SessionSeq +".";
			break;
		case BEGIN:
			TransactionID = TransactionID +"."+SessionSeq +"."+TransactionSeq;
			break;
		case END:
			TransactionID = TransactionID +"."+SessionSeq +".";
			break;
		default:
			break;
		}
		return TransactionID;
	}
	/**
	 * begin transaction
	 * @param ReadOnly (true:read, false:writable)
	 * @return SessionStateEnum
	 * @exception DBAccessException
	 */
	@Override
	public SessionStateEnum beginTransaction(boolean ReadOnly) throws DBAccessException  {
		if ( !sessionState.isPossibleProcess("beginTransaction"))
			throw new DBAccessException(sessionState.toString(),"["+ getTransactionID() + "] can not beginTransaction");
		
		this.ReadOnly = ReadOnly;
		thisSession.getTransaction().begin();
		
		if (TransactionSeq >= 2147483647 ) {
			TransactionSeq=1;
		}
		TransactionSeq++;
		sessionState=SessionStateEnum.BEGIN;
		return sessionState;
	}
	/**
	 * end the transaction
	 * @param CommitOK (true:commit,false:rollback)
	 * @return return true if success otherwise false.
	 * @exception DBAccessException
	 */
	@Override
	public SessionStateEnum endTransaction(boolean CommitOK) throws DBAccessException  {
		if ( !sessionState.isPossibleProcess("endTransaction"))
			throw new DBAccessException(sessionState.toString(),"["+ getTransactionID() + "] can not endTransaction");
		boolean CommitResult=false;
		try {
			if ( this.ReadOnly==false && CommitOK)
			{
				thisSession.flush();
				thisSession.getTransaction().commit();
			}
			else
				thisSession.getTransaction().rollback();
			
			sessionState=SessionStateEnum.OPEN;
		} catch (Exception e)	 {
			logger.error(e.toString());
			new DBAccessException(e.getMessage());
		}
		return sessionState;
	}
	/**
	 * get the sessionFactory
	 * @return sessionFactory
	 */
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * get current Session State
	 * @return SessionStateEnum
	 */
	@Override
	public SessionStateEnum getSessionState() {
		return sessionState;
	}
}

