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

import com.zaxxer.hikari.HikariDataSource;

import kr.co.codeJ.JPA.ComException.NationErrorMessages;
import kr.co.codeJ.JPA.ComException.PersonLanguage;
import kr.co.codeJ.JPA.DBAccessor.DBAccessManager;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManager;
import kr.co.codeJ.JPA.DBAccessor.DBSessionManagerImpl;


/** This is Configuration.
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Configuration
@EnableJpaRepositories(
        basePackages={"com.codeJ.BasicFrame","com.codeJ.JPA"},
        entityManagerFactoryRef = "DBSessionFactory",
        transactionManagerRef = "DBTransacionManager")
@Profile("dev")
public class JPADevConfigClass {
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
	/**
	 * make bean object of Map<String,DBCoreSessionManager> 
	 * @return Map<String,DBCoreSessionManager>
	 */
	@Bean(name="getMap")
	public Map<String,DBSessionManager> getMap() {
		return Collections.synchronizedMap(new HashMap<String,DBSessionManager>());
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
	
	@Bean(name = "DBSessionFactory")
	public SessionFactory getDBSessionFactory() {
		logger.info("JPA:{}:{}:{}:{}:{}:{}",driverClassName ,userName,passWord,jdbcUrl,maxPoolSize, minPoolSize );

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(defaultDataSource());
        factoryBean.setPackagesToScan("com.codeJ.*");
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
	@DependsOn({"getMap","DBSessionFactory"})
	public DBAccessManager getDBAccessManager() {
		DBAccessManager myaccessor=new DBAccessManager();
		return myaccessor;
	}

	@Bean(name="DBTransacionManager")
	public JpaTransactionManager getDBTransactionManager(
			@Qualifier("DBSessionFactory") EntityManagerFactory entityManagerFactory) {
			JpaTransactionManager transactionManager= new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(entityManagerFactory);
			return transactionManager;
	}
	
	@Bean(name="DBManager")
	@DependsOn({"DBSessionFactory" })
	public DBSessionManager getDBManager(SessionFactory sessionFactory) {
		DBSessionManager returnManager= new DBSessionManagerImpl(sessionFactory,Integer.parseInt(maxPoolSize));
		returnManager.startSessionManager("default");
		return returnManager;
	}
	
	@Bean(name="NationErrorMessages")
	@DependsOn({"DBCommService"})
	public NationErrorMessages getErrorMessages() {
		return new NationErrorMessages().defaultMessages();
	}
	
	@Bean(name="PersonLanguage")
	public PersonLanguage getPersonLanguage() {
		return new PersonLanguage();
	}
}