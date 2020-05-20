package com.udmtek.DBCore.DBAccessor;

/**
 * This is the interface module for the user of DBCore package.
 * @author mhjin < julu1 @ naver.com>
 * @version 0.0.1
 */
public interface DBCoreAccessManager {
	
	public DBCoreSessionManager makeSessionManager();
	/** 
	 * get a UnusedSession
	 * @return DBCoreSession
	 */
	public DBCoreSession getSession();
	
	/**
	 * end the transaction and set unused the currentSession
	 * @param DBCoreSession getting from getSesssion() or getSession(DBCoreSessionManager)
	 */
	public void releaseSession(DBCoreSession currentSession);
	
	/** 
	 * This is for another DataBase connection.
	 * To make Database Connection, have to specify in persistence.xml
	 * @param persistence-unit name in persistence.xml
	 * @return DBCoreSessionManager
	 */
	public DBCoreSessionManager createDBCoreSessionManager( String PersistenceUnitName);
	
	/** 
	 * This is for another DataBase connection.
	 * get the DBCoreSssion.
	 * @param DBCoreSessionManager making from createDBCoreSessionManager( String )
	 * @return DBCoreSession
	 */
	public DBCoreSession getSession (DBCoreSessionManager DBCoreSessionMgr);
	
	/**
	 * This is for another DataBase connection.
	 * Destroy DBCoreSessionManager. It does not use anymore.
	 * @param DBCoreSessionManager making from createDBCoreSessionManager( String )
	 */
	public void deleteDBCoreSessionManager(DBCoreSessionManager DBCoreSesionMgr);
}
