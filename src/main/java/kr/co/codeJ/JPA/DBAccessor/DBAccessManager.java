package kr.co.codeJ.JPA.DBAccessor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

/** This is DBSessionManager Factory class
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */

public class DBAccessManager  {
	private static Logger logger=LoggerFactory.getLogger(DBAccessManager.class);
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	@Qualifier("getMap")
	Map<String,DBSessionManager> PersistenceMap;
	
	
	/**
	 * get DBSesionManager
	 * @param String PersistenceUnit
	 * @return DBSessionManager
	 */
	public  DBSessionManager makeSessionManager(String PersistenceUnit) {
		if ( PersistenceUnit.equals("default"))
			return (DBSessionManager) context.getBean("DBManager");
		
		//if the SessionManager exist in the map, return existing SessionManager
		DBSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		if (returnManager != null)
		{
			return returnManager;
		}
		returnManager= new DBSessionManagerImpl(PersistenceUnit);
		returnManager.startSessionManager(PersistenceUnit);
		PersistenceMap.put(PersistenceUnit, returnManager);
		return returnManager;
	}
	
	/**
	 * close DBSessionManager
	 * @param String PersistenceUnit
	 */
	public void closeSessionManager(String PersistenceUnit) {
		DBSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		returnManager.closeAllSessions();
		PersistenceMap.remove(PersistenceUnit);
	}
}
