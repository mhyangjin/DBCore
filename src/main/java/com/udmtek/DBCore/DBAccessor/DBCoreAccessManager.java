package com.udmtek.DBCore.DBAccessor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/** This is implement class DBCoreAccessManager.
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
@Component
public class DBCoreAccessManager  {
	@Autowired
	ApplicationContext context;
	@Autowired
	Map<String,DBCoreSessionManager> PersistenceMap;
	
	public  DBCoreSessionManager makeSessionManager(String PersistenceUnit) {
		//if the SessionManager exist in the map, return existing SessionManager
		DBCoreSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		if (returnManager != null)
		{
			return returnManager;
		}
		
		returnManager= context.getBean(DBCoreSessionManager.class);
		returnManager.startSessionManager(PersistenceUnit);
		PersistenceMap.put(PersistenceUnit, returnManager);
		return returnManager;
	}
	
	public void closeSessionManager(String PersistenceUnit) {
		DBCoreSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		returnManager.coloseAllSessions();
		PersistenceMap.remove(PersistenceUnit);
	}
}
