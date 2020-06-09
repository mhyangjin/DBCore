package com.udmtek.DBCore.DBAccessor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
@Aspect
public class DefineAOP {
	@Autowired
	DBCoreAccessManager DBAccessor;
	private static final Logger logger=LoggerFactory.getLogger(DefineAOP.class);
	
	public DefineAOP() {
		// TODO Auto-generated constructor stub
		
	}
	
	@Around("@annotation(DBCoreTransactional)&& @annotation(target)") 
	public Object DBCoreTransactional(ProceedingJoinPoint joinpoint, DBCoreTransactional target) throws Throwable {
		DBCoreSessionManager myManager;
		String PersistenceUnitName=target.PersistUnit();
		myManager=DBAccessor.makeSessionManager(PersistenceUnitName);
		
		DBCoreSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			
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
			 if ( BeginOK )
				 currSession.endTransaction(false);
			 	logger.info("[DBCore] Exception throws in {} {}",joinpoint.getSignature(), e.toString());
		 }
		 finally {
			 myManager.closeSession(currSession);
		 }
		 return result;
	 }
	

	@Around("@annotation(DBCoreReadTransactional)&& @annotation(target)")
	 public Object DBCoreReadTransactional(ProceedingJoinPoint joinpoint, DBCoreReadTransactional target) throws Throwable {
		DBCoreSessionManager myManager;
		String PersistenceUnitName=target.PersistUnit();
		myManager=DBAccessor.makeSessionManager(PersistenceUnitName);
		Object result=null;
		
		DBCoreSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			
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
			 if ( BeginOK )
				 currSession.endTransaction(false);
			 logger.info("[DBCore] Exception throws in {} {}",joinpoint.getSignature(), e.toString());
		 }
		finally {
			 myManager.closeSession(currSession);
		 }
		 return result;
	 }
}
