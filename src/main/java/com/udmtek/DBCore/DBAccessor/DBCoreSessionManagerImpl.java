package com.udmtek.DBCore.DBAccessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.AOPLogger;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
@Component
public class DBCoreSessionManagerImpl implements DBCoreSessionManager {
	//get the initSessionCount and MAXSessionCount in the application.properties file
	@Value("${initSessionCount}")
	private String SessionCountStr;
	@Value("${MAXSessionCount}")
	private String MAXSessionCountStr;
	
	@Getter
	@Setter
	private int SessionCount;
	

	@Getter
	@Setter
	private int MAXSessionCount;
	
	HashSet<DBCoreSession> UsingSessions=null;
	HashSet<DBCoreSession> UnusigSessions=null;
		
	public DBCoreSessionManagerImpl() {
		// TODO Auto-generated constructor stub
		SessionCount=Integer.parseInt(SessionCountStr);
		MAXSessionCount=Integer.parseInt(MAXSessionCountStr);
	}
	
	public void printValues() {
		String msg=this.getClass().getName() + ":initSessionCount=" + SessionCountStr
				  + ":MAXSessionCount=" + MAXSessionCountStr;
		DBCoreLogger.printInfo(msg);
	}
	
	@Override
	public void startManager() {
		// TODO Auto-generated method stub
		if(!checkSessionCount()) {
			//Exception throw!!!
		}
		
		try
		{
			//using Collections.synchronizedSet() is for safety in multi-thread env.
			UsingSessions=(HashSet<DBCoreSession>) Collections.synchronizedSet(new HashSet< DBCoreSession >());
			UnusigSessions=(HashSet<DBCoreSession>) Collections.synchronizedSet(new HashSet< DBCoreSession >());
		} catch (Exception e) {
			if (UsingSessions != null) {
				String msg=this.getClass().getName() + ":Exception thows when create HashSet<UsingSessions>";
				DBCoreLogger.printInfo(msg);
				UsingSessions.clear();
				UsingSessions=null;
			}
			else {
				String msg=this.getClass().getName() + ":Exception thows when create HashSet<UnusingSessions>";
				DBCoreLogger.printInfo(msg);				
			}
			throw e;
		}
		
		createSessions(SessionCount);
	}

	
	@Override
	public DBCoreSession getUnusingSession() {
		// TODO Auto-generated method stub
		
		DBCoreSession currSession=null;
		//if All session is used, create Session
		if (UnusigSessions.isEmpty()) {
			currSession=this.createSession();
		}
		else {
			currSession=this.removeUnusingSesion(null);
		}
		return currSession;
	}

	@Override
	public void endUsingSession(DBCoreSession currentSession) {
		// TODO Auto-generated method stub
		this.insertUnusingSession(currentSession);
	
	}
	
	private boolean checkSessionCount()
	{
		boolean returnValue=true;
		if ( SessionCount < 0 ) {
			String msg="Initial Session Count has larger than 0, but value is " + SessionCount;
			DBCoreLogger.printError(msg);
			return false;
		}
		
		if ( MAXSessionCount < 0) {
			String msg="Initial Session Count has larger than 0, but value is " + MAXSessionCount;
			DBCoreLogger.printError(msg);
			return false;			
		}
		
		if ( SessionCount > MAXSessionCount ) {
			String msg="SessionCount{" + SessionCount +"} has not larger than MAXSessionCount{"+ MAXSessionCount +"} ";
			DBCoreLogger.printError(msg);
			return false;			
		}
		
		return returnValue;
	}
	
	private DBCoreSession createSession() {
		return null;
	}
	
	private void createSessions(int createSessionCount) {
		for (int i=0; i< createSessionCount; i++) {
					
		}
		return;
	}
	
	
	private DBCoreSession insertUnusingSession(DBCoreSession insertSession) {
		DBCoreSession returnSession=null;
		
		//move insertSession from UsionSessions to UnusingSessions
		if (insertSession != null) {
			returnSession= insertSession;
			//Always do UnusionSessions first. 
			this.UnusigSessions.add(insertSession);
			this.UsingSessions.remove(insertSession);
		
		}
		return returnSession;
	}
	
	private DBCoreSession removeUnusingSesion(DBCoreSession removeSession) {
		DBCoreSession returnSession=null;
		
		//if removeSesion is null, get any session.
		if ( removeSession == null ) {
			Iterator<DBCoreSession> it=UnusigSessions.iterator();
			returnSession=(DBCoreSession) it.next();
		}
		//move returnSession from UnusingSessions to UsionSessions
		//Always do UnusionSessions first. 
		this.UnusigSessions.remove(returnSession);
		this.UsingSessions.add(returnSession);
		return returnSession;
	}

}
