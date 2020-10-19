package kr.co.codeJ.JPA.ComUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Component
@Aspect
public class LogAOP {
	private static final Logger logger=LoggerFactory.getLogger(LogAOP.class);
	
	public LogAOP() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This is for test. make trace log every calling of method.
	 * @param JoinPoint
	 */
	@Before("execution(public * com.codeJ.*.*..*.*(..))")
	public void logBefore(JoinPoint joinpoint) {
		logger.trace("[JPA] The {} begins",joinpoint.getSignature()); 
	}

	
	@Before("execution(public * kr.co.codeJ.*.*..*.*(..))")
	public void krlogBefore(JoinPoint joinpoint) {
		logger.trace("[JPA] The {} begins",joinpoint.getSignature()); 
	}
}
