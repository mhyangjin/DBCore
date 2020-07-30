package com.udmtek.DBCoreTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import com.codeJ.MVCTestGen.testWebGenerator;
/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
//@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.udmtek")
@ComponentScan(basePackages = "com.codeJ")
@EnableAsync
public class DbCoreApplication {
	static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context=SpringApplication.run(DbCoreApplication.class, args);
		testWebGenerator testGen= (testWebGenerator)context.getBean("webGen") ;
		testGen.makeTestPage();
		System.out.println("========================================test=========================");
		
	}
}
