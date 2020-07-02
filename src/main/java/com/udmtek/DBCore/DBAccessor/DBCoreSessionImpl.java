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
<<<<<<< HEAD
	public enum State { 
		INIT,
		OPENDED,
		BEGINED ,
		End;
		}
  
	@Autowired
	ApplicationContext context;
	
	SessionState sessionControl;
	
	private SessionFactory mySessionFactory=null;
	private String SessionName="";
	State sessionState;

	private EntityManager myentityManager=null;
	private Session thisSession=null;
	private int EntityManagerSeq=0;
	
	private Transaction currTransaction=null;
=======
	@Autowired
	ApplicationContext context;
	
	private SessionFactory sessionFactory=null;
	private String SessionName="";
	SessionStateEnum sessionState;	

	private Session thisSession=null;
	private int SessionSeq=0;
	
>>>>>>> udmtek
	private int TransactionSeq=0;
	private boolean ReadOnly;
	
	
	@Override
	public void readyConnect(SessionFactory sessionFactory, String argSessionName) {
		this.sessionFactory=sessionFactory;
		SessionName=argSessionName;
<<<<<<< HEAD
		sessionState=State.INIT;
=======
		sessionState=SessionStateEnum.INIT;
>>>>>>> udmtek
	}
	
	@Override
	public boolean openSession()
	{
<<<<<<< HEAD
		thisSession=mySessionFactory.openSession();
		myentityManager=mySessionFactory.createEntityManager();
		if (EntityManagerSeq >= 2147483647 ) {
			EntityManagerSeq=1;
		}
		EntityManagerSeq++;
		sessionState=State.OPENDED;
=======
		if ( !sessionState.isPossibleProcess("openSession"))
			DBCoreLogger.printDBError("openSession" + sessionState.isPossibleProcess("openSession"));
		
		thisSession=sessionFactory.openSession();
		if (SessionSeq >= 2147483647 ) {
			SessionSeq=1;
		}
		SessionSeq++;
		sessionState=SessionStateEnum.OPEN;
>>>>>>> udmtek
		return true;
	}
	
	@Override
	public boolean closeSession() {
		if ( !sessionState.isPossibleProcess("closeSession"))
		DBCoreLogger.printDBError("closeSession" + sessionState.isPossibleProcess("closeSession"));
		
		thisSession.close();
		thisSession=null;
<<<<<<< HEAD
		myentityManager=null;
		sessionState=State.INIT;
		return true;
	}
	
=======
		sessionState=SessionStateEnum.INIT;
		return true;
	}
	
	@Override
	public Session getThisSession() {
		return thisSession;
	}
>>>>>>> udmtek
	
	@Override
	public String getTransactionID() {
		String TransactionID=SessionName;
		switch (sessionState) {
		case INIT:
<<<<<<< HEAD
			TransactionID = TransactionID + ":";
			break;
		case OPENDED:
			TransactionID = TransactionID +"["+EntityManagerSeq +":]";
			break;
		case BEGINED:
			TransactionID = TransactionID +"["+EntityManagerSeq +":"+TransactionSeq + "]";
=======
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
>>>>>>> udmtek
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
<<<<<<< HEAD
		currTransaction=thisSession.getTransaction();
		currTransaction.begin();
=======
		thisSession.getTransaction().begin();
>>>>>>> udmtek
		
		if (TransactionSeq >= 2147483647 ) {
			TransactionSeq=1;
		}
		TransactionSeq++;
<<<<<<< HEAD
		sessionState=State.BEGINED;
=======
		sessionState=SessionStateEnum.BEGIN;
>>>>>>> udmtek
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
			DBCoreLogger.printDBError(e.toString());
			throw (e);
		}
<<<<<<< HEAD
		
		currTransaction=null;
		sessionState=State.INIT;
=======
>>>>>>> udmtek
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
	
	@Override
	public State getSessionState()
	{
		return sessionState;
	}
	
	
	@Override
	public boolean isOpened() {
		if ( sessionState ==State.INIT )
			return false;
		else
			return true;
	}
	@Override
	public boolean isbeginedTransacion() {
		if ( sessionState ==State.BEGINED )
			return true;
		else
			return false;
	}

}
