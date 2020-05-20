package com.udmtek.DBCore.ComUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOPLogger {

	private static final Logger logger=LoggerFactory.getLogger(AOPLogger.class);
	
	public AOPLogger() {
		// TODO Auto-generated constructor stub
	}
	
	@Before("execution(* com.udmtek.DBCore.DBAccessor.*.*(..))")
	public void logBefore(JoinPoint joinpoint) {
		logger.info("[DBCore] The {} begins with {}",joinpoint.getSignature(), joinpoint.toShortString());
	}

}
