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

import com.udmtek.DBCore.ComException.DBException;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/**
 * This is AOP specification for Transaction controll
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
	public Object DBCoreTransactional(ProceedingJoinPoint joinpoint, DBCoreTransactional target) throws DBException,Throwable {
		DBCoreSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			 DBCoreLogger.printDBError("There is no available session "+joinpoint.getSignature());
			 return null;
		}
		 SessionStateEnum sessionState=SessionStateEnum.OPEN;
		 try {
			 Object result=null;
			 sessionState=currSession.beginTransaction(false);  //beginTransaction readOnly=false
			 //////////////////원래 호출하려는 method////////////////
			 result=joinpoint.proceed(joinpoint.getArgs());
			 //////////////////////////////////////////////////
			 if ( sessionState == SessionStateEnum.BEGIN )
				 sessionState=currSession.endTransaction(true); //endTransaction commit=OK
			 return result;
		 }
		 catch (DBException e) {
			 throw e;
		 }
		 catch (Throwable e) {
			 DBCoreLogger.printDBInfo("Exception throws in "+joinpoint.getSignature()+" "+ e.toString());
			 throw e;
		 }
		 finally {
			 //If sessionState is BEGIN, This means commit transaction was failed.
			 // So have to Rollback transaction.
			 if ( sessionState == SessionStateEnum.BEGIN )
				 sessionState=currSession.endTransaction(false);
			 if ( sessionState == SessionStateEnum.OPEN )
				myManager.closeSession(currSession);
		 }
	 }
	

	@Around("@annotation(DBCoreReadTransactional)&& @annotation(target)")
	 public Object DBCoreReadTransactional(ProceedingJoinPoint joinpoint, DBCoreReadTransactional target) throws DBException,Throwable {
		Object result=null;
		DBCoreSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			 DBCoreLogger.printDBError("There is no available session "+joinpoint.getSignature());
			 return null;
		}
		 SessionStateEnum sessionState=SessionStateEnum.OPEN;
		try {
			sessionState=currSession.beginTransaction(true);
			 //////////////////원래 호출하려는 method////////////////
			 result=joinpoint.proceed(joinpoint.getArgs());
			 return result;
			 //////////////////////////////////////////////////
		 }
		 catch (DBException e) {
			 throw e;
		 }
		 catch (Throwable e) {
			 DBCoreLogger.printDBInfo("Exception throws in "+joinpoint.getSignature()+" "+ e.toString());
			 throw e;
		 }
		 finally {
			 if ( sessionState == SessionStateEnum.BEGIN )
				 sessionState=currSession.endTransaction(false);
			 if ( sessionState == SessionStateEnum.OPEN )
				myManager.closeSession(currSession);
		 }

	 }
}
