package com.udmtek.DBCore.DBAccessor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/** Implementation of the DBSessionManager
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
@Component
@Scope(value = "prototype" )
public class DBCoreSessionManagerImpl implements DBCoreSessionManager{
	private String PersistenceUnit;
	private SessionFactory ssfImpl=null;
	private int MaxSessionPoolSize;
	private Set<DBCoreSession> unusingSessions=null;
	private Set<DBCoreSession> usingSessions=null;
	
	@Autowired
	ApplicationContext context;
	
	@SuppressWarnings("unchecked")
	@Override
	public void startSessionManager(String argPersistUnit) {
		PersistenceUnit=argPersistUnit;
		ssfImpl=(SessionFactory) Persistence.createEntityManagerFactory(PersistenceUnit);
		DBCoreLogger.printInfo("SessionFactory:" + ssfImpl.toString());
		Map<String,Object> properties = ssfImpl.getProperties();
		MaxSessionPoolSize= Integer.parseInt(properties.get("hibernate.hikari.maximumPoolSize").toString());
		
		if (unusingSessions == null)
		{
			unusingSessions=( Set<DBCoreSession> )context.getBean("getList");
			usingSessions=( Set<DBCoreSession> )context.getBean("getList");
		}
		createEmptySessions();
	}
	@Override
	public String getPersistUnit() {
		return PersistenceUnit;
	}
	@Override
	public SessionFactory getSessionFactory() {
		return ssfImpl;
	}
	@Override
	public void printValues() {
		String msg=this.getClass().getName() + "PersistenceUnitName=" + PersistenceUnit 
											 + " MaxPoolSize=" + MaxSessionPoolSize;
		DBCoreLogger.printInfo(msg);
	}
	
	@Override
	public DBCoreSession openSession() {
		// TODO Auto-generated method stub
		
		DBCoreSession currSession=null;
		currSession=findUnusingSession();
		
		String msg="UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
		return currSession;
	}
	
	@Override
	public DBCoreSession openSession(int retryNo, int waitTime) {
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
		
		String msg="[OPEN]UnusingSessions:" + unusingSessions.size() + " UsingSessions:" + usingSessions.size();
		DBCoreLogger.printInfo(msg);
	
		return currSession;
	}


	@Override
	public boolean closeSession(DBCoreSession currSession) {
		boolean result=currSession.closeSession();
		
		unusingSessions.add(currSession);
		usingSessions.remove(currSession);
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
			DBCoreSession newSession=context.getBean(DBCoreSession.class);
			String SessionName=PersistenceUnit + i;
			newSession.readyConnect(ssfImpl, SessionName);
			unusingSessions.add(newSession);
		}
	}
	
	public void coloseAllSessions() {
		//before delete map, have to call closeSession() all session in usigSessions.
		usingSessions.clear();
		unusingSessions.clear();
		ssfImpl.close();
	}
}
