package com.udmtek.DBCore.DBAccessor;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;


/** Implementation of the DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
@Component("DBCoreSession")
@Scope(value = "prototype")
public class DBCoreSessionImpl implements DBCoreSession {
	@Autowired
	ApplicationContext context;
	
	private SessionFactory sessionFactory=null;
	private String SessionName="";
	SessionStateEnum sessionState;	

	private Session thisSession=null;
	private int SessionSeq=0;
	
	private int TransactionSeq=0;
	private boolean ReadOnly;
	
	
	@Override
	public void readyConnect(SessionFactory sessionFactory, String argSessionName) {
		this.sessionFactory=sessionFactory;
		SessionName=argSessionName;
		sessionState=SessionStateEnum.INIT;
	}
	
	@Override
	public boolean openSession()
	{
		if ( !sessionState.isPossibleProcess("openSession"))
			DBCoreLogger.printDBError("openSession" + sessionState.isPossibleProcess("openSession"));
		
		thisSession=sessionFactory.openSession();
		if (SessionSeq >= 2147483647 ) {
			SessionSeq=1;
		}
		SessionSeq++;
		sessionState=SessionStateEnum.OPEN;
		return true;
	}
	
	@Override
	public boolean closeSession() {
		if ( !sessionState.isPossibleProcess("closeSession"))
		DBCoreLogger.printDBError("closeSession" + sessionState.isPossibleProcess("closeSession"));
		
		thisSession.close();
		thisSession=null;
		sessionState=SessionStateEnum.INIT;
		return true;
	}
	
	public Session getThisSession() {
		return thisSession;
	}
	
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

	@Override
	public boolean beginTransaction(boolean ReadOnly) {
		if ( !sessionState.isPossibleProcess("beginTransaction"))
			DBCoreLogger.printDBError("beginTransaction" + sessionState.isPossibleProcess("beginTransaction"));
		
		this.ReadOnly = ReadOnly;
		thisSession.getTransaction().begin();
		
		if (TransactionSeq >= 2147483647 ) {
			TransactionSeq=1;
		}
		TransactionSeq++;
		sessionState=SessionStateEnum.BEGIN;
		return true;
	}

	@Override
	public boolean endTransaction(boolean CommitOK) {
		if ( !sessionState.isPossibleProcess("endTransaction"))
			DBCoreLogger.printDBError("endTransaction" + sessionState.isPossibleProcess("endTransaction"));
		
		boolean CommitResult=false;
		
		try {
			if ( this.ReadOnly==false && CommitOK)
			{
				thisSession.flush();
				thisSession.getTransaction().commit();
			}
			else
				thisSession.getTransaction().rollback();
			CommitResult=true;
			sessionState=SessionStateEnum.OPEN;
		} catch (Exception e)	 {
			e.printStackTrace();
			throw (e);
		}
		return CommitResult;
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public boolean isBeginTransaction() {
		if (sessionState == SessionStateEnum.BEGIN) return true;
		return false;
	}

}
