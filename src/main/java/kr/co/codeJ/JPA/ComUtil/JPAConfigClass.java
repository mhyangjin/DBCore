package kr.co.codeJ.JPA.ComUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import kr.co.codeJ.JPA.ComException.NationErrorMessages;
import kr.co.codeJ.JPA.ComException.PersonLanguage;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManager;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManagerImpl;


/** This is Configuration.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Configuration
//@EnableJpaRepositories(
//        basePackages={"kr.co.codeJ.JPA.model.*"},
//        entityManagerFactoryRef = "JPAEntityManagerFactory")
@Profile("!dev")
public class JPAConfigClass {
	private static Logger logger=LoggerFactory.getLogger(JPADevConfigClass.class);
	
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
	@Value("${hibernate.connection.provider_class}") 
	String providerClass;
	@Value("kr.co.CodeJ.modelScanDir")
	String scanDir;
	@Value("hibernate.show_sql")
	String showSql;
	
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
	
	@Bean
	public SessionFactory  entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		if (showSql.toUpperCase().contains("TRUE") )
			vendorAdapter.setShowSql(true);
		else
			vendorAdapter.setShowSql(false);
		
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(defaultDataSource());
        factoryBean.setPackagesToScan(scanDir);
        
//        Properties hikariproperties = new Properties();
//        hikariproperties.put("hibernate.hikari.maximumPoolSize",maxPoolSize);
//        factoryBean.setJpaProperties(hikariproperties);
//        
        Map<String, Property> jpaProperties=new HashMap<>();
        jpaProperties.put("hibernate.dialect", Property.forName(dialect));
       factoryBean.setJpaPropertyMap(jpaProperties);
	   factoryBean.afterPropertiesSet();
       
       return (SessionFactory)factoryBean.getNativeEntityManagerFactory();
	}
	
	@Bean(name="DBSessionManager")
	public DBSessionManager getDBManager() {
		DBSessionManager returnManager= new DBSessionManagerImpl(entityManagerFactory(),Integer.parseInt(maxPoolSize));
		returnManager.startSessionManager();
		return returnManager;
	}
	
	
//	@Bean(name="JPATransactionManager")
//	public JpaTransactionManager transactionManager(
//			@Qualifier("JPAEntityManagerFactory") EntityManagerFactory emf) {
//	    JpaTransactionManager transactionManager = new JpaTransactionManager();
//	    transactionManager.setEntityManagerFactory(emf);
//	 
//	    return transactionManager;
//	}
}