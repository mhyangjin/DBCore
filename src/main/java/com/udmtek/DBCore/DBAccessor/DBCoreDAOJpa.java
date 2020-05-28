package com.udmtek.DBCore.DBAccessor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

public class DBCoreDAOJpa<T> implements DBCoreDAO<T> {
	private Class<T> type;
	protected EntityManager myEntityMgr=null;
	
	public DBCoreDAOJpa(Class<T> type) {
		super();
		this.type = type;
	}
	
	public void setEntityManager(EntityManager argEntityMgr) {
		this.myEntityMgr = argEntityMgr;
	}
	
	@Override
	public T get(DBCoreKey key) {
		// TODO Auto-generated method stub
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
		return (T) myEntityMgr.find(type, key);
	}
	
	@Override
	public List<T> getfromJPQL(String Jquery) {
		// TODO Auto-generated method stub
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
		return myEntityMgr.createQuery(Jquery,type).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getfromSQL(String sqlquery) {
		// TODO Auto-generated method stub
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
		return myEntityMgr.createNativeQuery(sqlquery,type).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return null;
		}
	
		return myEntityMgr.createQuery("select m from " + type.getName() + " m").getResultList();
	}

	@Override
	public void save(T object) throws DataAccessException {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return;
		}
		// TODO Auto-generated method stub
		myEntityMgr.persist(object);
	}

	@Override
	public void delete(T object) throws DataAccessException {
		if (myEntityMgr == null ) {
			DBCoreLogger.printError("Entity Manager is null.");
			return;
		}
		// TODO Auto-generated method stub
		myEntityMgr.remove(object);
	}

}
