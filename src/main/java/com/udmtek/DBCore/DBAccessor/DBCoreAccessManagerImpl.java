package com.udmtek.DBCore.DBAccessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/** This is implement class DBCoreAccessManager.
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
@Component
public class DBCoreAccessManagerImpl implements DBCoreAccessManager {
	@Autowired
	private DBCoreSessionManager DBCoreSessionMgr;
		
	public DBCoreSessionManager makeSessionManager() {
		System.out.println("DBCoreSessionManager:makeSessionManager");
		return DBCoreSessionMgr;
	}
	
	
	@Override
	public DBCoreSession getSession() {
		// TODO Auto-generated method stub
		DBCoreSession unUsedSess=null;
		unUsedSess=DBCoreSessionMgr.getUnusingSession();
		return unUsedSess;
	}

	@Override
	public void releaseSession(DBCoreSession currentSession) {
		// TODO Auto-generated method stub
		DBCoreSessionManager DBCoreSessMgr=null;
		DBCoreSessMgr=currentSession.getDBCoreSessionManager();
		DBCoreSessMgr.endUsingSession(currentSession);
	}

	@Override
	public DBCoreSessionManager createDBCoreSessionManager(String PersistenceUnitName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBCoreSession getSession(DBCoreSessionManager DBCoreSessionMgr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDBCoreSessionManager(DBCoreSessionManager DBCoreSesionMgr) {
		// TODO Auto-generated method stub
		
	}


}
