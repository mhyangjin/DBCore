package kr.co.codeJ.JPA.DBAccessor;

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

import kr.co.codeJ.JPA.ComException.DBException;
/**
 * This is AOP specification for Transaction controll
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
@Aspect
public class TransactionAOP {
	private static Logger logger=LoggerFactory.getLogger(TransactionAOP.class);
	
	@Autowired
	@Qualifier("DBManager")
	DBSessionManager myManager;
		
	@Around("@annotation(DBTransactional)&& @annotation(target)") 
	public Object DBTransactional(ProceedingJoinPoint joinpoint, DBTransactional target) throws DBException,Throwable {
		DBSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			 logger.error("There is no available session {}",joinpoint.getSignature());
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
			 logger.info("Exception throws in {} {}",joinpoint.getSignature(),e.toString());
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
	

	@Around("@annotation(DBReadTransactional)&& @annotation(target)")
	 public Object DBReadTransactional(ProceedingJoinPoint joinpoint, DBReadTransactional target) throws DBException,Throwable {
		Object result=null;
		DBSession currSession=myManager.openSession(3, 100);
		if ( currSession == null) {
			logger.error("There is no available session {}",joinpoint.getSignature());
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
			 logger.info("Exception throws in {} {}",joinpoint.getSignature(),e.toString());
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
