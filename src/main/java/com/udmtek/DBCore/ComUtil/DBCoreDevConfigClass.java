package com.udmtek.DBCore.ComUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import com.udmtek.DBCore.ComException.NationErrorMessages;
import com.udmtek.DBCore.ComException.PersonLanguage;
import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManagerImpl;
import com.zaxxer.hikari.HikariDataSource;


/** This is Configuration.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Configuration
@EnableJpaRepositories(
        basePackages={"com.udmtek.DBCore", "com.codeJ.BasicFrame"},
        entityManagerFactoryRef = "DBCoreSessionFactory",
        transactionManagerRef = "DBCoreTransacionManager")
@Profile("dev")
public class DBCoreDevConfigClass {
	@Value("${hibernate.connection.driver_class}") 
	String driverClassName;
	@Value("${hibernate.connection.username}")
	String userName;
	@Value("${hibernate.connection.password}") 
	String passWord;
	@Value("${hibernate.connection.url}") 
	String jdbcUrl;
	
	@Value("${hibernate.hikari.maximumPoolSize}") 
	String maxPoolSize;
	@Value("${hibernate.hikari.minimumIdle}") 
	String minPoolSize;
	@Value("${hibernate.dialect}") 
	String dialect;
	/**
	 * make bean object of Map<String,DBCoreSessionManager> 
	 * @return Map<String,DBCoreSessionManager>
	 */
	@Bean(name="getMap")
	public Map<String,DBCoreSessionManager> getMap() {
		return Collections.synchronizedMap(new HashMap<String,DBCoreSessionManager>());
	}
	

	@Bean
	public DataSource defaultDataSource() {
			
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
		dataSource.setMinimumIdle(Integer.parseInt(minPoolSize));
		return dataSource;
	}
	
	@Bean(name = "DBCoreSessionFactory")
	public SessionFactory DBCoreSessionFactory() {
		DBCoreLogger.printInfo("DBCORE:" + driverClassName 
	               + ":" + userName
	               + ":" + passWord
	               + ":" + jdbcUrl
	               + ":" + maxPoolSize
	               + ":" + minPoolSize );

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(defaultDataSource());
        factoryBean.setPackagesToScan("com.codeJ.BasicFrame.*");
 //       factoryBean.setPackagesToScan("com.udmtek.DBCore.*");
        Properties hikariproperties = new Properties();
        hikariproperties.put("hibernate.hikari.maximumPoolSize",maxPoolSize);
        factoryBean.setJpaProperties(hikariproperties);
        Map<String, Property> jpaProperties=new HashMap<>();
        jpaProperties.put("hibernate.dialect", Property.forName(dialect));
       factoryBean.setJpaPropertyMap(jpaProperties);
	   factoryBean.afterPropertiesSet();
       
       return(SessionFactory) factoryBean.getNativeEntityManagerFactory();
	}
	
	@Bean(name="DBAccessManager")
	@DependsOn({"getMap","DBCoreSessionFactory"})
	public DBCoreAccessManager getdbCoreAccessManager() {
		DBCoreAccessManager myaccessor=new DBCoreAccessManager();
		return myaccessor;
	}

	@Bean(name="DBCoreTransacionManager")
	public JpaTransactionManager DBCoretransactionManager(
			@Qualifier("DBCoreSessionFactory") EntityManagerFactory entityManagerFactory) {
			JpaTransactionManager transactionManager= new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(entityManagerFactory);
			return transactionManager;
	}
	
	@Bean(name="DBManager")
	@DependsOn({"DBCoreSessionFactory" })
	public DBCoreSessionManager getDBManager(SessionFactory sessionFactory) {
		DBCoreSessionManager returnManager= new DBCoreSessionManagerImpl(sessionFactory,Integer.parseInt(maxPoolSize));
		returnManager.startSessionManager("default");
		return returnManager;
	}
	
	@Bean(name="nationErrorMessages")
	@DependsOn({"DBCoreCommService"})
	public NationErrorMessages getErrorMessages() {
		return new NationErrorMessages().defaultMessages();
	}
	
	@Bean(name="personLanguage")
	public PersonLanguage getPersonLanguage() {
		return new PersonLanguage();
	}
}