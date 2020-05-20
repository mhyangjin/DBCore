package com.udmtek.DBCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;

//@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class DbCoreApplication {
	static ConfigurableApplicationContext context;
	
	public static void TestDBCoreAccessManager() {
		DBCoreAccessManager mymanager=context.getBean(DBCoreAccessManager.class);
		DBCoreSessionManager SessionMgr=mymanager.makeSessionManager();
		
		SessionMgr.printValues();
		SessionMgr.startManager();
	}
	
	public static void main(String[] args) {
		
		context=SpringApplication.run(DbCoreApplication.class, args);
		
		TestDBCoreAccessManager();
		
	}
}
