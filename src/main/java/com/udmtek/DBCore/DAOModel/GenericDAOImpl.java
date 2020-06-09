package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreSession;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;


/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public class GenericDAOImpl <T> implements GenericDAO<T> {
	private Class<T> type;
	@Autowired
	ApplicationContext context;
	
	public GenericDAOImpl(Class<T> type) {
		super();
		this.type = type;
	}
	
	
	@Override
	public T get(Serializable key) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return (T) currSession.find(type.getSuperclass(), key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GenericKeyDAOImpl<?> getKeyDAOImpl(String tableName) {
		String DAOName=tableName + "IdDAOImpl";
		GenericKeyDAOImpl keyDAOImpl=(GenericKeyDAOImpl)context.getBean(DAOName);
		return keyDAOImpl;	
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromJPQL(String Jquery) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return currSession.createQuery(Jquery).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromSQL(String sqlquery) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return currSession.createNativeQuery(sqlquery,type.getSuperclass()).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return currSession.createQuery("select m from " + type.getSuperclass().getName() + " m").getResultList();
	}

	@Override
	public void insert(T object) throws DataAccessException {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		currSession.persist(object);
	}

	@Override
	public void save(T Object) {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		currSession.merge(Object);
	}

	@Override
	public void delete(T object) throws DataAccessException {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		currSession.remove(object);
	}
	
	@Override
	public void delete(Serializable object) throws DataAccessException {
		EntityManager currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		T findObject=get(object);
		currSession.remove(findObject);
	}
	
}
