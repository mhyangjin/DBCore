package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;


/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public class GenericDAOImpl <T extends EntityDAO> implements GenericDAO<T> {
	private Class<T> type;
	protected EntityManager myEntityMgr=null;
	@Autowired
	ApplicationContext context;
	
	public GenericDAOImpl(Class<T> type) {
		super();
		this.type = type;
	}
	
	public void setEntityManager(EntityManager argEntityMgr) {
		this.myEntityMgr = argEntityMgr;
	}
	
	@Override
	public T get(Serializable key) {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
		return (T) myEntityMgr.find(type, key);
	}

	@Override
	public Serializable getKey(T object) {
		object.getKey();
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public GenericKeyDAOImpl<?> getKeyDAOImpl(String tableName) {
		String DAOName=tableName + "IdDAOImpl";
		GenericKeyDAOImpl keyDAOImpl=(GenericKeyDAOImpl)context.getBean(DAOName);
		return keyDAOImpl;	
	}


	@Override
	public List<T> getfromJPQL(String Jquery) {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
		return myEntityMgr.createQuery(Jquery,type).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromSQL(String sqlquery) {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
		return myEntityMgr.createNativeQuery(sqlquery,type).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
			if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
	
		return myEntityMgr.createQuery("select m from " + type.getName() + " m").getResultList();
	}

	@Override
	public void insert(T object) throws DataAccessException {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return;
		}
		myEntityMgr.persist(object);
	}

	@Override
	public void save(T Object) {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return;
		}
		myEntityMgr.merge(Object);
	}

	@Override
	public void delete(T object) throws DataAccessException {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return;
		}
		myEntityMgr.remove(object);
	}
	
	@Override
	public void delete(Serializable object) throws DataAccessException {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return;
		}
		T findObject=get(object);
		myEntityMgr.remove(findObject);
	}
	
}
