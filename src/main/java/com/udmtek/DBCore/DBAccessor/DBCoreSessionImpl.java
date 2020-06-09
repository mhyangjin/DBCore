package com.udmtek.DBCore.DBAccessor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DAOModel.GenericDAOImpl;


/** Implementation of the DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
@Component
@Scope(value = "prototype")
public class DBCoreSessionImpl implements DBCoreSession {
	public enum State { 
		INIT,
		OPENDED,
		BEGINED ,
		End;
		}
  
	@Autowired
	ApplicationContext context;
	
	private EntityManagerFactory myEntityFactory=null;
	private String SessionName="";
	State sessionState;	

	private EntityManager thisSession=null;
	private int EntityManagerSeq=0;
	
	private int TransactionSeq=0;
	private boolean ReadOnly;
	
	
	@Override
	public void readyConnect(EntityManagerFactory argFactory, String argSessionName) {
		myEntityFactory=argFactory;
		SessionName=argSessionName;
		sessionState=State.INIT;
	}
	
	@Override
	public boolean openSession()
	{
		thisSession=myEntityFactory.createEntityManager();
		if (EntityManagerSeq >= 2147483647 ) {
			EntityManagerSeq=1;
		}
		EntityManagerSeq++;
		sessionState=State.OPENDED;
		return true;
	}
	
	@Override
	public boolean closeSession() {
		thisSession.close();
		thisSession=null;
		sessionState=State.INIT;
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
		case OPENDED:
			TransactionID = TransactionID +"["+EntityManagerSeq +":]";
			break;
		case BEGINED:
			TransactionID = TransactionID +"["+EntityManagerSeq +":"+TransactionSeq + "]";
		default:
			break;
		}
		return TransactionID;
	}

	@Override
	public boolean beginTransaction(boolean ReadOnly) {
		this.ReadOnly = ReadOnly;
		thisSession.getTransaction().begin();
		
		if (TransactionSeq >= 2147483647 ) {
			TransactionSeq=1;
		}
		TransactionSeq++;
		sessionState=State.BEGINED;
		return true;
	}

	@Override
	public boolean endTransaction(boolean CommitOK) {
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
		} catch (Exception e)	 {
			e.printStackTrace();
			throw (e);
		}
		
		sessionState=State.INIT;
		return CommitResult;
	}
	
	@Override
	public EntityManagerFactory getEntityFactory() {
		return myEntityFactory;
	}

}
