package com.udmtek.DBCore.DBAccessor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

/** This is DBCoreSessionManager Factory class
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */

public class DBCoreAccessManager  {
	private static Logger logger=LoggerFactory.getLogger(DBCoreAccessManager.class);
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	@Qualifier("getMap")
	Map<String,DBCoreSessionManager> PersistenceMap;
	
	
	/**
	 * get DBCoreSesionManager
	 * @param String PersistenceUnit
	 * @return DBCoreSessionManager
	 */
	public  DBCoreSessionManager makeSessionManager(String PersistenceUnit) {
		if ( PersistenceUnit.equals("default"))
			return (DBCoreSessionManager) context.getBean("DBManager");
		
		//if the SessionManager exist in the map, return existing SessionManager
		DBCoreSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		if (returnManager != null)
		{
			return returnManager;
		}
		returnManager= new DBCoreSessionManagerImpl(PersistenceUnit);
		returnManager.startSessionManager(PersistenceUnit);
		PersistenceMap.put(PersistenceUnit, returnManager);
		return returnManager;
	}
	
	/**
	 * close DBCoreSessionManager
	 * @param String PersistenceUnit
	 */
	public void closeSessionManager(String PersistenceUnit) {
		DBCoreSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		returnManager.closeAllSessions();
		PersistenceMap.remove(PersistenceUnit);
	}
}
