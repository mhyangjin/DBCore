package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;
import com.udmtek.DBCoreTest.model.FactoryInfo;


/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */

public class GenericDAOImpl <T extends Entity> implements GenericDAO<T> {
	private Class<T> type;
	private boolean needConvert=false;
	private Class<?> convertType;
	@Autowired
	ApplicationContext context;
	
	public GenericDAOImpl(Class<T> type) {
		super();
		this.type = type;
		if ( type.getSimpleName().endsWith("Info")) {
			needConvert=true;
			convertType=type.getSuperclass();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<T> convertType(List<Object> object) {
		if (object == null) return null;
			List<T> result=object
					.stream()
					.map(e -> (T) e)
					.collect(Collectors.toList());
			return result;
	}
	
	@Override
	public T get(Serializable key) {
		
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		
		Entity result;
		if ( needConvert ) {
			result=(Entity)currSession.find(convertType, key);
			result=result.getInfo(type);
		}
		else
			result = currSession.find(type, key);

		return (T)result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromJPQL(String Jquery) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return convertType(currSession.createQuery(Jquery).getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromSQL(String sqlquery) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			return convertType(currSession.createNativeQuery(sqlquery,convertType).getResultList());
		else 
			return currSession.createQuery("select m from " + type.getName() + " m").getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			return convertType(currSession.createQuery("select m from " + convertType.getName() + " m").getResultList());
		else 
			return currSession.createQuery("select m from " + type.getName() + " m").getResultList();
			
	}

	@Override
	public void insert(T object) throws DataAccessException {
		
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.persist(convertType.cast(object));
		else
			currSession.persist(object);
	}

	@Override
	public void save(T object) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.merge(convertType.cast(object));
		else
			currSession.merge(object);
	}

	@Override
	public void delete(T object) throws DataAccessException {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.remove(convertType.cast(object));
		else
			currSession.remove(object);
	}
	
	@Override
	public void delete(Serializable object) throws DataAccessException {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.remove( convertType.cast(get(object)));
		else
			currSession.remove(get(object));
	}
	
}
