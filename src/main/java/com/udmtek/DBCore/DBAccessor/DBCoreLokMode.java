package com.udmtek.DBCore.DBAccessor;

import org.hibernate.LockMode;

/** enum of the LockMode
 * @author julu1 <julu1 @ naver.com >
 * @version version 0.1.0
 */
public enum DBCoreLokMode {
	READLOCK(LockMode.READ),
	WRITELOCK(LockMode.WRITE),
	;
	
	LockMode hibernateLock;
	DBCoreLokMode(LockMode hibernateLock) {
		this.hibernateLock = hibernateLock;
	}
	
	public LockMode getHibernateLock() {
		return hibernateLock;
	}
}
