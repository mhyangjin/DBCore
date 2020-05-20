package com.udmtek.DBCore.TestModule;
import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.support.OpenSessionInterceptor;

import com.udmtek.DBCore.DBAccessor.DBCoreAccessManager;

import jdk.nashorn.internal.runtime.Context;

@EnableAutoConfiguration
@ComponentScan
public class JpaMain {
	
	public static void DBCoreAccessManagetTest() {
		System.out.println("DBCoreAccessManagetTest");
		
		
	}
	public static void makeEntityManager() {
		System.out.println("make EntityManagerFactory");
		//SessionFactoryDelegatingImpl ssfImpl = new SessionFactoryDelegatingImpl((SessionFactoryImplementor) Persistence.createEntityManagerFactory("DBCoreJpa"));
		//EntityManager em= ssfImpl.createEntityManager();
		//EntityTransaction tx= em.getTransaction(		);
		SessionFactory ssfImpl= (SessionFactory) Persistence.createEntityManagerFactory("DBCoreJpa");
		System.out.println("make entity manager");
		
		Session currSession=ssfImpl.openSession();
		
		System.out.println("beginTransaction");	
		Transaction tx=null;
		try {
			tx=currSession.beginTransaction();
			
			System.out.println("in Transaction");	
			tx.commit();
		} catch (Exception e) {
			 e.printStackTrace();
			 if( tx != null) tx.rollback();
		} finally {
			currSession.close();
		}
		
		ssfImpl.close();
	}
}
