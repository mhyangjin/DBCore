package com.udmtek.DBCore.DBAccessor;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * interface DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version 0.0.1
 */
public interface DBCoreSession{
	
	/**
	 * start the transaction 
	 * @param SessionType ("READ_ONLY" or "READ_WRITE" )
	 * @return return true if success otherwise false.
	 */
	
	public boolean connectSession();
	
	public boolean beginTransaction(boolean ReadOnly);
	
	/**
	 * end the transaction
	 * @param true if you want commit, otherwise false( rollback)
	 * @return return true if success otherwise false.
	 */
	public boolean endTransaction(boolean CommitOK);
	
	public String getTransactionID();
	
	public DBCoreSessionManager getDBCoreSessionManager();
}
