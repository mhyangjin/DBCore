package kr.co.codeJ.JPA.sampleCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
//@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "kr.co.codeJ")
@EnableAsync
public class JPAApplication {
	static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context=SpringApplication.run(JPAApplication.class, args);

	}
}
