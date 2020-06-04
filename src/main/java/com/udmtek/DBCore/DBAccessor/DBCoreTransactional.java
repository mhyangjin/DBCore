package com.udmtek.DBCore.DBAccessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** define annotation. 
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBCoreTransactional {
	/**
	 * PersisUnit : persistence-unit name.
	 */
	String PersistUnit();

}