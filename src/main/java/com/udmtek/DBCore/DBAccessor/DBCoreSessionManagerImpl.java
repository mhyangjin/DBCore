package com.udmtek.DBCore.DBAccessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/** Implementation of the DBSessionManager
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */

public class DBCoreSessionManagerImpl implements DBCoreSessionManager{
	private String PersistenceUnit;
	private SessionFactory sessionFactory=null;
	private int MaxSessionPoolSize;
	private Set<DBCoreSession> unusingSessions=null;
	private Set<DBCoreSession> usingSessions=null;
	
	@Autowired
	ApplicationContext context;

	public DBCoreSessionManagerImpl() {}
	
	public DBCoreSessionManagerImpl(String argPersistUnit) {
		PersistenceUnit=argPersistUnit;
		EntityManagerFactory entityFactory=Persistence.createEntityManagerFactory(PersistenceUnit);
		sessionFactory= (SessionFactory)entityFactory;
		Map<String,Object> properties =entityFactory.getProperties();
		MaxSessionPoolSize= Integer.parseInt(properties.get("hibernate.hikari.maximumPoolSize").toString());
	}
	
	public DBCoreSessionManagerImpl(SessionFactory sessionFactory, int MaxSessionPoolSize) 
	{
		this.sessionFactory=sessionFactory;
		this.MaxSessionPoolSize= MaxSessionPoolSize;
	}
	
	@Override
	public void startSessionManager(String argPersistUnit) {
		PersistenceUnit=argPersistUnit; 
		if (MaxSessionPoolSize == 0) MaxSessionPoolSize=1;
		if (unusingSessions == null)
		{
			unusingSessions=Collections.synchronizedSet(new HashSet<DBCoreSession>());
			usingSessions=Collections.synchronizedSet(new HashSet<DBCoreSession>());
		}
		createEmptySessions();
	}
	
	@Override
	public String getPersistUnit() {
		return PersistenceUnit;
	}
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Override
	public void printValues() {
		String msg=this.getClass().getName() + "PersistenceUnitName=" + PersistenceUnit 
											 + " MaxPoolSize=" + MaxSessionPoolSize;
		DBCoreLogger.printInfo(msg);
	}
	
	@Override
	public DBCoreSession openSession() {
		DBCoreSession currSession=null;
		currSession=findUnusingSession();
		String msg="UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
		DBSession.set(currSession);
		return currSession;
	}
	
	@Override
	public DBCoreSession openSession(int retryNo, long waitTime) {
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
		
		String msg="[OPEN]UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
		
		if ( currSession != null)
			//set Thread Local variable
			DBSession.set(currSession);
		
		return currSession;
	}

	@Override
	public boolean closeSession(DBCoreSession currSession) {
		boolean result=currSession.closeSession();
		unusingSessions.add(currSession);
		usingSessions.remove(currSession);
		DBSession.remove();
		String msg="[CLOSE]UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
		return result;
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
		currSession.openSession();			
		return currSession;
	}
	
	
	private void createEmptySessions() {
		for ( int i=0; i < MaxSessionPoolSize; i++)
		{
			DBCoreSession newSession=new DBCoreSessionImpl();
			String SessionName=PersistenceUnit +"Session"+ i;
			newSession.readyConnect(sessionFactory, SessionName);
			unusingSessions.add(newSession);
		}
	}
	
	public void coloseAllSessions() {
		//before delete map, have to call closeSession() all session in usigSessions.
		usingSessions.clear();
		unusingSessions.clear();
		sessionFactory.close();
	}
}
