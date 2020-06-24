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
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		Entity result;
		if ( needConvert ) {
			result=(Entity)currSession.get(convertType, key);
			result=result.getInfo(type);
		}
		else
			result = currSession.get(type, key);

		return (T)result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromJPQL(String Hquery) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return convertType(currSession.createQuery(Hquery).list());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromSQL(String sqlquery) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return convertType(currSession.createSQLQuery(sqlquery).list());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			return convertType(currSession.createQuery("select m from " + convertType.getName() + " m").list());
		else 
			return currSession.createQuery("select m from " + type.getName() + " m").list();
			
	}

	@Override
	public void insert(T object) throws DataAccessException {
		
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.persist(convertType.cast(object));
		else
			currSession.persist(object);
	}

	@Override
	public void save(T object) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.merge(convertType.cast(object));
		else
			currSession.merge(object);
	}

	@Override
	public void delete(T object) throws DataAccessException {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.delete(convertType.cast(object));
		else
			currSession.delete(object);
	}
	
	@Override
	public void delete(Serializable object) throws DataAccessException {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		if ( needConvert )
			currSession.delete( convertType.cast(get(object)));
		else
			currSession.delete(get(object));
	}
	
}
