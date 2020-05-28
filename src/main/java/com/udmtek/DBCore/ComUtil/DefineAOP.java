package com.udmtek.DBCore.ComUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;

@Component
@Aspect
public class DefineAOP {
	@Autowired
	DBCoreAccessManager DBAccessor;
	private static final Logger logger=LoggerFactory.getLogger(DefineAOP.class);
	
	public DefineAOP() {
		// TODO Auto-generated constructor stub
	}
	
	@Before("execution(public * com.udmtek.DBCore.DBAccessor..*.*(..))")
	public void logBefore(JoinPoint joinpoint) {
		logger.info("[DBCore] The {} begins",joinpoint.getSignature());
	}

	 @Around("@annotation(DBCoreTransactional)")
	 public Object DBCoreTransactional(ProceedingJoinPoint joinpoint) throws Throwable {
		 DBCoreSession currSession=(DBCoreSession) joinpoint.getArgs()[0];
		 Object result=null;
		 try {
			 currSession.beginTransaction(false);
		 }
		 catch (Exception e) {
			 logger.info("[DBCore] Exception throws in {} {}",joinpoint.getSignature(), e.toString());
		 }
		 
		 try {
			 result=joinpoint.proceed(joinpoint.getArgs());
		 }
		 catch (Exception e) {
			 logger.info("[DBCore] Exception throws in {} {}",joinpoint.getSignature(), e.toString());
		 }
		 
		 try {
			 currSession.endTransaction(true);
		 }
		 catch (Exception e) {
			 logger.info("[DBCore] Exception throws in {} {}",joinpoint.getSignature(), e.toString());
		 }
		 
		 return result;
	 }
}
