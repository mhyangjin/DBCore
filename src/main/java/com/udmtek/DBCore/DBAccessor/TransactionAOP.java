package com.udmtek.DBCore.DBAccessor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
@Aspect
public class TransactionAOP {
	@Autowired
	@Qualifier("DBManager")
	DBCoreSessionManager myManager;
		
	@Around("@annotation(DBCoreTransactional)&& @annotation(target)") 
	public Object DBCoreTransactional(ProceedingJoinPoint joinpoint, DBCoreTransactional target) throws Throwable {
		DBCoreSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			 DBCoreLogger.printDBError("There is no available session "+joinpoint.getSignature());
			 return null;
		}
			
		 Object result=null;
		 boolean BeginOK=false;
		 try {
			 BeginOK=currSession.beginTransaction(false);
			 //////////////////원래 호출하려는 method////////////////
			 result=joinpoint.proceed(joinpoint.getArgs());
			 //////////////////////////////////////////////////
			 currSession.endTransaction(true);
		 }
		 catch (Exception e) {
			 DBCoreLogger.printDBInfo("Exception throws in "+joinpoint.getSignature()+" "+ e.toString());
			 if ( BeginOK )
				 currSession.endTransaction(false);
		 }
		 finally {
			 myManager.closeSession(currSession);
		 }
		 return result;
	 }
	

	@Around("@annotation(DBCoreReadTransactional)&& @annotation(target)")
	 public Object DBCoreReadTransactional(ProceedingJoinPoint joinpoint, DBCoreReadTransactional target) throws Throwable {
		Object result=null;
		
		DBCoreSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			 DBCoreLogger.printDBError("There is no available session "+joinpoint.getSignature());
			 return null;
		}
		 boolean BeginOK=false;
		try {
			BeginOK=currSession.beginTransaction(true);
			 //////////////////원래 호출하려는 method////////////////
			 result=joinpoint.proceed(joinpoint.getArgs());
			 //////////////////////////////////////////////////
		 	 currSession.endTransaction(false);
		 }
		 catch (Exception e) {
			 DBCoreLogger.printDBInfo("Exception throws in "+joinpoint.getSignature()+" "+ e.toString());
		 if ( BeginOK )
				 currSession.endTransaction(false);
		 }
		finally {
			 myManager.closeSession(currSession);
		 }
		 return result;
	 }
}
