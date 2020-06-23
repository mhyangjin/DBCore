package com.udmtek.DBCoreTest.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;


@Component
@Scope(value = "prototype" )
public class FactoryInfo extends FactoryDAO {
	@Autowired
	static ApplicationContext context;
	
	public FactoryInfo() {}
	

	public FactoryInfo (String memberCorpid, String factoryid) {
			super(memberCorpid, factoryid );
		}

}
