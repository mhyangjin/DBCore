package com.udmtek.DBCore.DBAccessor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
	
	private EntityManagerFactory myEntityFactory=null;
	private String SessionName="";
	SessionStateEnum sessionState;	

	private EntityManager thisSession=null;
	private int EntityManagerSeq=0;
	
	private int TransactionSeq=0;
	private boolean ReadOnly;
	
	
	@Override
	public void readyConnect(EntityManagerFactory argFactory, String argSessionName) {
		myEntityFactory=argFactory;
		SessionName=argSessionName;
		sessionState=SessionStateEnum.INIT;
	}
	
	@Override
	public boolean openSession()
	{
		if ( !sessionState.isPossibleProcess("openSession"))
			DBCoreLogger.printInfo("openSession" + sessionState.isPossibleProcess("openSession"));
		thisSession=myEntityFactory.createEntityManager();
		if (EntityManagerSeq >= 2147483647 ) {
			EntityManagerSeq=1;
		}
		EntityManagerSeq++;
		sessionState=SessionStateEnum.OPEN;
		return true;
	}
	
	@Override
	public boolean closeSession() {
		if ( !sessionState.isPossibleProcess("closeSession"))
		DBCoreLogger.printInfo("closeSession" + sessionState.isPossibleProcess("closeSession"));
		
		thisSession.close();
		thisSession=null;
		sessionState=SessionStateEnum.INIT;
		return true;
	}
	
	public EntityManager getThisSession() {
		return thisSession;
	}
	
	@Override
	public String getTransactionID() {
		String TransactionID=SessionName;
		switch (sessionState) {
		case INIT:
			TransactionID = TransactionID + ":";
			break;
		case OPEN:
			TransactionID = TransactionID +"["+EntityManagerSeq +":]";
			break;
		case BEGIN:
			TransactionID = TransactionID +"["+EntityManagerSeq +":"+TransactionSeq + "]";
			break;
		case END:
			TransactionID = TransactionID +"["+EntityManagerSeq +":]";
			break;
		default:
			break;
		}
		return TransactionID;
	}

	@Override
	public boolean beginTransaction(boolean ReadOnly) {
		if ( !sessionState.isPossibleProcess("beginTransaction"))
			DBCoreLogger.printInfo("beginTransaction" + sessionState.isPossibleProcess("beginTransaction"));
		
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
			DBCoreLogger.printInfo("endTransaction" + sessionState.isPossibleProcess("endTransaction"));
		
		boolean CommitResult=false;
		
		try {
			if ( this.ReadOnly==false && CommitOK)
			{
				DBCoreLogger.printInfo("COMMIT");
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
	public EntityManagerFactory getEntityFactory() {
		return myEntityFactory;
	}

}
