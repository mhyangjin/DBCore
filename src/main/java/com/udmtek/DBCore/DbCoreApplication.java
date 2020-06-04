package com.udmtek.DBCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@EnableAutoConfiguration
@ComponentScan
@EnableAsync
public class DbCoreApplication {
	
	static ConfigurableApplicationContext context;
	

	
	public static void main(String[] args) {
		
		context=SpringApplication.run(DbCoreApplication.class, args);

		
	}
}
