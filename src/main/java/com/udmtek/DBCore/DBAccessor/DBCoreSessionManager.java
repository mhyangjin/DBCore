package com.udmtek.DBCore.DBAccessor;

/**
 * This is for management DBCoreSession set.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.0.1
 */
public interface DBCoreSessionManager {

	public void startManager();
	public void printValues();

	/**
	 * get the unused DBCoreSesion.
	 * @return DBCoreSession
	 */
	public DBCoreSession getUnusingSession();
	
	/**
	 * move used Seesion from usedSession Set to unuseSession set.
	 * @param currentSession
	 */
	public void endUsingSession(DBCoreSession currentSession); 
}
