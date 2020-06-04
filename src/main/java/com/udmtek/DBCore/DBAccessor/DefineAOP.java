package com.udmtek.DBCore.DBAccessor;

import java.lang.reflect.Field;

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
			 currSession.beginTransaction(false);
			 BeginOK=true;
			 //호출한 class에 member 변수에 DBCoreSession이 선언되어 있으면  setting하여 준다.
			 //DBCoreSession 상단에 @Setter annotation이 붙거나 "set"+"변수이름(첫글자 대문자)"로 선언된 함수가 있어야 한다.
			 Object targetObject=joinpoint.getTarget();
			 Field[] attrs=targetObject.getClass().getDeclaredFields();
			 for (int i=0; i < attrs.length ; i++)  {
				  Field attr=attrs[i];
				  String AttrName=attr.getName();
				  if (  attr.getType().getName().endsWith("DBCoreSession")) {
					  String SetterName="set" + AttrName.toUpperCase().charAt(0) + AttrName.substring(1);
					  targetObject.getClass().getMethod(SetterName,DBCoreSession.class).invoke(targetObject, currSession);
				  }
			  }
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
			 currSession.beginTransaction(true);
			 //호출한 class에 member 변수에 DBCoreSession이 선언되어 있으면  setting하여 준다.
			 //DBCoreSession 상단에 @Setter annotation이 붙거나 "set"+"변수이름(첫글자 대문자)"로 선언된 함수가 있어야 한다.
			 Object targetObject=joinpoint.getTarget();
			 Field[] attrs=targetObject.getClass().getDeclaredFields();
			 for (int i=0; i < attrs.length ; i++)  {
				  Field attr=attrs[i];
				  String AttrName=attr.getName();
				  if (  attr.getType().getName().endsWith("DBCoreSession")) {
					  String SetterName="set" + AttrName.toUpperCase().charAt(0) + AttrName.substring(1);
					  targetObject.getClass().getMethod(SetterName,DBCoreSession.class).invoke(targetObject, currSession);
				  }
			  }
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
