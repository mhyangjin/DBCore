package com.udmtek.DBCoreTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;
/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
//@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.udmtek")
@EnableAsync
public class DbCoreApplication {
	static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context=SpringApplication.run(DbCoreApplication.class, args);

	}
}
