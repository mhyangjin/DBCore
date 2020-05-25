package com.udmtek.DBCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.udmtek.DBCore.TestModule.JpaMain;

@EnableAutoConfiguration
@ComponentScan
@EnableAsync
public class DbCoreApplication {
	
	static ConfigurableApplicationContext context;
	

	
	public static void main(String[] args) {
		
		context=SpringApplication.run(DbCoreApplication.class, args);
//		JpaMain.makeEntityManager();
		
	}
}
