package com.udmtek.DBCore.DBAccessor;

import java.util.Map;

import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

import lombok.Getter;

/** This is implement class DBCoreAccessManager.
 * @author julu1 <julu1 @ naver.com >
 * @version 
 */
@Component
public class DBCoreAccessManager  {
	@Autowired
	ApplicationContext context;
	@Autowired
	Map<String,DBCoreSessionManager> PersistenceMap;
	
	public  DBCoreSessionManager makeSessionManager(String PersistenceUnit) {
		//if the SessionManager exist in the map, return existing SessionManager
		DBCoreSessionManager returnManager=PersistenceMap.get(PersistenceUnit);
		if (returnManager != null)
		{
			return returnManager;
		}
		
		returnManager=(DBCoreSessionManager) new DBCoreSessionManagerImpl(PersistenceUnit);
		PersistenceMap.put(PersistenceUnit, returnManager);
		return returnManager;
	}
}
