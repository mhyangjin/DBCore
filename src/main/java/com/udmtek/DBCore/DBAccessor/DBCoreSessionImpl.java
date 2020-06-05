/**
 * 
 */
package com.udmtek.DBCore.DBAccessor;

import javax.persistence.EntityManager;
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
@ComponentScan("com.udmtek.model")
public class DBCoreSessionImpl implements DBCoreSession {
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
	private int TransactionSeq=0;
	private boolean ReadOnly;
	
	
	@Override
	public void readyConnect(SessionFactory argFactory, String argSessionName) {
		mySessionFactory=argFactory;
		SessionName=argSessionName;
		sessionState=State.INIT;
	}
	
	@Override
	public boolean openSession()
	{
		thisSession=mySessionFactory.openSession();
		myentityManager=mySessionFactory.createEntityManager();
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
		myentityManager.close();
		thisSession=null;
		myentityManager=null;
		sessionState=State.INIT;
		return true;
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
		currTransaction=thisSession.getTransaction();
		currTransaction.begin();
		
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
		if ( currTransaction == null )	{
			//Exception throw
			return CommitResult;
		}

		try {
			if ( this.ReadOnly==false && CommitOK)
			{
				DBCoreLogger.printInfo("COMMIT");
				myentityManager.flush();
				currTransaction.commit();
			}
			else
				currTransaction.rollback();
			CommitResult=true;
		} catch (Exception e)	 {
			e.printStackTrace();
			throw (e);
		}
		
		currTransaction=null;
		sessionState=State.INIT;
		return CommitResult;
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return mySessionFactory;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return myentityManager;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public <T> T getDAOImpl(Class<T> type) {
		T TImpl=context.getBean( type);
		((GenericDAOImpl) TImpl).setEntityManager(myentityManager);
		return TImpl;	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GenericDAOImpl<?> getDAOImpl(String TableName) {
		String DAOName=TableName + "DAOImpl";
		GenericDAOImpl DAOImpl=(GenericDAOImpl)context.getBean(DAOName);
		DAOImpl.setEntityManager(myentityManager);
		return DAOImpl;	
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
