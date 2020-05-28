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


/** Implementation of the DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
@Component
@Scope(value = "prototype")
@ComponentScan("com.udmtek.model")
public class DBCoreSessionImpl implements DBCoreSession {
	/**
	 * 
	 */
	private Session thisSession=null;
	private Transaction currTransaction=null;
	private String SessionName="";
	private int TransactionSeq=0;
	private SessionFactory mySessionFactory=null;
	private EntityManager myentityManager;
	@Autowired
	ApplicationContext context;
	
	@Override
	public void readyConnect(SessionFactory argFactory, String argSessionName) {
		// TODO Auto-generated constructor stub
		mySessionFactory=argFactory;
		SessionName=argSessionName;
	}
	@Override
	public boolean openSession()
	{
		thisSession=mySessionFactory.openSession();
		myentityManager=mySessionFactory.createEntityManager();
		return true;
	}
	
	@Override
	public boolean closeSession() {
		thisSession.close();
		myentityManager.close();
		thisSession=null;
		myentityManager=null;
		return true;
	}
	
	
	@Override
	public String getTransactionID() {
		return SessionName+"_"+TransactionSeq;
	}

	@Override
	public boolean beginTransaction(boolean ReadOnly) {
		// TODO Auto-generated method stub
		currTransaction=thisSession.getTransaction();
		if (TransactionSeq > 2147483647 ) {
			TransactionSeq=1;
		}
		TransactionSeq++;
		currTransaction.begin();
		return true;
	}

	@Override
	public boolean endTransaction(boolean CommitOK) {
		// TODO Auto-generated method stub
		boolean CommitResult=false;
		if ( currTransaction == null )	{
			//Exception throw
			return CommitResult;
		}
		
		try {
			if ( CommitOK)
			{
				myentityManager.flush();
				currTransaction.commit();
			}
			else
				currTransaction.rollback();
			CommitResult=true;
		} catch (Exception e)	 {
			e.printStackTrace();
			currTransaction.rollback();
		}
		finally {
			currTransaction=null;
		}
		return CommitResult;
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return mySessionFactory;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return myentityManager;
	}
	
	public <T> T getDAOImpl(Class<T> type) {
		T TImpl=context.getBean( type);
		DBCoreDAOJpa Tmp=(DBCoreDAOJpa) TImpl;
		Tmp.setEntityManager(myentityManager);
		return TImpl;	
	}

}
