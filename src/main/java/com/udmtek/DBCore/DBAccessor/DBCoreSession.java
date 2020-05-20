package com.udmtek.DBCore.DBAccessor;

import org.hibernate.SessionFactory;

/**
 * interface DBCoreSession
 * @author julu1 <julu1 @ naver.com >
 * @version 0.0.1
 */
public interface DBCoreSession extends SessionFactory {
	

	/**
	 * get generated TransactionID (UUID of Session + sequence number of Transaction )
	 * @return transaction id
	 */
	public int getTranseactionID();
	
	/**
	 * start the transaction 
	 * @param SessionType ("READ_ONLY" or "READ_WRITE" )
	 * @return return true if success otherwise false.
	 */
	public boolean beginTransaction(String SessionType);
	
	/**
	 * end the transaction
	 * @param true if you want commit, otherwise false( rollback)
	 * @return return true if success otherwise false.
	 */
	public boolean endTransaction(boolean CommitOK);
	
	/**
	 * return DBCoressionManager
	 * @return DBCoressionManager 
	 */
	public DBCoreSessionManager getDBCoreSessionManager(); 
}
