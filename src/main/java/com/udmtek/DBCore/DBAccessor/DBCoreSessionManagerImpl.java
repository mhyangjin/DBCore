package com.udmtek.DBCore.DBAccessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.persistence.Persistence;
import org.hibernate.SessionFactory;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import lombok.Getter;
import lombok.Setter;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
public class DBCoreSessionManagerImpl implements DBCoreSessionManager{
	@Getter
	@Setter
	private String PersistenceUnit;
	@Getter
	private SessionFactory ssfImpl=null;
	@Getter
	private int MaxSessionPoolSize;
	private Set<DBCoreSession> unusingSessions=null;
	private Set<DBCoreSession> usingSessions=null;
	
	public DBCoreSessionManagerImpl(String argPersistUnit) {
		PersistenceUnit=argPersistUnit;
		ssfImpl=(SessionFactory) Persistence.createEntityManagerFactory(PersistenceUnit);
		Map<String,Object> properties = ssfImpl.getProperties();
		MaxSessionPoolSize= Integer.parseInt(properties.get("hibernate.hikari.maximumPoolSize").toString());
		
		if (unusingSessions == null)
		{
			unusingSessions=Collections.synchronizedSet(new HashSet<DBCoreSession>());
			usingSessions=Collections.synchronizedSet(new HashSet<DBCoreSession>());
		}
		createEmptySessions();
	}
	
	@Override
	public void printValues() {
		String msg=this.getClass().getName() + "PersistenceUnitName=" + PersistenceUnit 
											 + " MaxPoolSize=" + MaxSessionPoolSize;
		DBCoreLogger.printInfo(msg);
	}
	
	@Override
	public DBCoreSession getSession() {
		// TODO Auto-generated method stub
		
		DBCoreSession currSession=null;
		currSession=findUnusingSession();
		
		String msg="UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
		return currSession;
	}
	
	@Override
	public DBCoreSession getSession(int retryNo, int waitTime) {
		// TODO Auto-generated method stub
		DBCoreSession currSession=null;
		if ( retryNo <=0 ) {
			DBCoreLogger.printError("retryNo has to larger than 0");
			return null;
		}
		
		if ( waitTime < 0 ) {
			DBCoreLogger.printError("waitTime has to larger than 0");
			return null;
		}
		
		for ( int i=0; i < retryNo; i++ )
		{
			currSession=findUnusingSession();
			if (currSession ==null) {
				try {
					String msg="All Session is used.. Sleep and retry ..." + i;
					DBCoreLogger.printInfo(msg);
					Thread.sleep(waitTime);
				}
				catch (Exception e) {
					//do nothing
				}
			}
			else
				break;
		}
		
		String msg="UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
	
		return currSession;
	}

	private DBCoreSession findUnusingSession() {
		
		DBCoreSession currSession=null;

		synchronized( this ) {
			if( unusingSessions.size() == 0 )
			{
				DBCoreLogger.printInfo("All Session is used!!");
				//have to add throwing the exception later
				return currSession;
			}
		
		//use synchronized for stability in simultaneous access
			Iterator<DBCoreSession> it=unusingSessions.iterator();
			currSession=it.next();
			unusingSessions.remove(currSession);
		}
		
		usingSessions.add(currSession);
		currSession.connectSession();			
		return currSession;
	}
	
	public void returnSession(DBCoreSession currSession) {
		unusingSessions.add(currSession);
		usingSessions.remove(currSession);
		String msg="UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
	}
	
	private void createEmptySessions() {
		for ( int i=0; i < MaxSessionPoolSize; i++)
		{
			DBCoreSession newSession=(DBCoreSession) new DBCoreSessionImpl(this,i+1);
			unusingSessions.add(newSession);
		}
	}
}
