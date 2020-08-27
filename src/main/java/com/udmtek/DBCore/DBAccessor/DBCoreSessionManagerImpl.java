package com.udmtek.DBCore.DBAccessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/** Implementation of the DBSessionManager
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
public class DBCoreSessionManagerImpl implements DBCoreSessionManager{
	private static Logger logger=LoggerFactory.getLogger(DBCoreSessionManagerImpl.class);
	
	private String PersistenceUnit;
	private SessionFactory sessionFactory=null;
	private int MaxSessionPoolSize;
	private Set<DBCoreSession> unusingSessions=null;
	private Set<DBCoreSession> usingSessions=null;
	
	@Autowired
	ApplicationContext context;

	/**
	 * constructor
	 */
	public DBCoreSessionManagerImpl() {}
	
	/**
	 * constructor
	 * @param argPersistUnit
	 */
	public DBCoreSessionManagerImpl(String argPersistUnit) {
		PersistenceUnit=argPersistUnit;
		EntityManagerFactory entityFactory=Persistence.createEntityManagerFactory(PersistenceUnit);
		sessionFactory= (SessionFactory)entityFactory;
		Map<String,Object> properties =entityFactory.getProperties();
		MaxSessionPoolSize= Integer.parseInt(properties.get("hibernate.hikari.maximumPoolSize").toString());
	}
	
	/**
	 * constructor
	 * @param sessionFactory
	 * @param MaxSessionPoolSize
	 */
	public DBCoreSessionManagerImpl(SessionFactory sessionFactory, int MaxSessionPoolSize) 
	{
		this.sessionFactory=sessionFactory;
		this.MaxSessionPoolSize= MaxSessionPoolSize;
	}
	/**
	 * make connection with specified Database
	 * @param String  : persistence-unit name in peristence.xml
	 */
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
	/**
	 * get persistence-unit name of connected database
	 * @return String
	 */
	@Override
	public String getPersistUnit() {
		return PersistenceUnit;
	}
	/**
	 * get SessionFactory.
	 * @return SessionFactory
	 */
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * print persistence UnitName and maximumPoolSize 
	 */
	@Override
	public void printValues() {
		logger.info("{} PersistenceUnitName={} MaxPoolSize={}",this.getClass().getName(),
																PersistenceUnit, MaxSessionPoolSize);
	}
	/**
	 * get the unused DBCoreSesion. return null if there is no unused session immediately.
	 * @return DBCoreSession
	 */
	@Override
	public DBCoreSession openSession() {
		DBCoreSession currSession=null;
		currSession=findUnusingSession();
		DBSession.set(currSession);
		logger.trace("UnusingSessions:{} UsingSessions:{}",unusingSessions.size(),usingSessions.size());
		DBSession.set(currSession);
		return currSession;
	}
	/**
	 * get the unused DBCoreSesion.
	 * @param int retryNo has to larger than 0
	 * @param int waitTime milli-seconds 
	 * @return DBCoreSession
	 */	
	@Override
	public DBCoreSession openSession(int retryNo, long waitTime) {
		DBCoreSession currSession=null;
		if ( retryNo <=0 ) {
			logger.error("retryNo has to larger than 0");
			return null;
		}
		
		if ( waitTime < 0 ) {
			logger.error("waitTime has to larger than 0");
			return null;
		}
		
		for ( int i=0; i < retryNo; i++ )
		{
			currSession=findUnusingSession();
			if (currSession ==null) {
				try {
					logger.info("All Session is used.. Sleep and retry ...{}",i);
					Thread.sleep(waitTime);
				}
				catch (Exception e) {
					//do nothing
				}
			}
			else
				break;
		}
		if ( currSession != null)
			//set Thread Local variable
			DBSession.set(currSession);

		logger.trace("[OPEN]UnusingSessions:{}  UsingSessions:{}",unusingSessions.size(),usingSessions.size());
		
		if ( currSession != null)
			//set Thread Local variable
			DBSession.set(currSession);
		
		return currSession;
	}
	/**
	 * close session.
	 * @param opended session.
	 * @return SessionStateEnum
	 */
	@Override
	public SessionStateEnum closeSession(DBCoreSession currSession) {
		SessionStateEnum state=currSession.closeSession();
		unusingSessions.add(currSession);
		usingSessions.remove(currSession);
		DBSession.remove();
		logger.trace("[CLOSE]UnusingSessions:{}  UsingSessions:{}",unusingSessions.size(),usingSessions.size());
		return state;
	}
	/**
	 * close session.
	 * @param opended session.
	 * @return SessionStateEnum
	 */
	public void closeAllSessions() {
		//before delete map, have to call closeSession() all session in usigSessions.
		usingSessions.clear();
		unusingSessions.clear();
		sessionFactory.close();
	}
	
	private void createEmptySessions() {
		for ( int i=0; i < MaxSessionPoolSize; i++)
		{
			DBCoreSession newSession=new DBCoreSessionImpl();
			String SessionName=PersistenceUnit + i;
			newSession.readyConnect(sessionFactory, SessionName);
			unusingSessions.add(newSession);
		}
	}
	private DBCoreSession findUnusingSession() {
		DBCoreSession currSession=null;
		synchronized( this ) {
			if( unusingSessions.size() == 0 )
			{
				logger.info("All Session is used!!");
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
}
