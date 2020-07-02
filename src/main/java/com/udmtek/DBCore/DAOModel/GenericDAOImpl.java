package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import com.udmtek.DBCore.DBAccessor.DBCoreLokMode;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public class GenericDAOImpl<E extends DBCoreEntity,D extends DBCoreDTO, M extends DBCoreDTOMapper<E,D>>
			implements GenericDAO<E,D,M> {
	private Class<E> entityType;
	private Class<D> dtoType;
	private M mapperObject;
	
	@Autowired
	ApplicationContext context;
	
	public GenericDAOImpl(Class<E> entityType, Class<D> dtoType) {
		super();
		this.entityType = entityType;
		this.dtoType = dtoType;
	}
	
	@Autowired
	public void setMapperObject(M mapperObject) {
		this.mapperObject = mapperObject;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public D get(Serializable key) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		DBCoreEntity result;
		result = currSession.get(entityType, key);
		return (D) mapperObject.toDto((E)result);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public D get(Serializable key, DBCoreLokMode lockMode) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		DBCoreEntity result;
		result = currSession.get(entityType, key,lockMode.getHibernateLock());
		return (D) mapperObject.toDto((E)result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromJPQL(String Hquery) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		List<E> entities=currSession.createQuery(Hquery).list();
		
		return (List<D>) mapperObject.toDto(entities);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(String sqlquery) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		List<Map<String, Object>> entities= currSession.createSQLQuery(sqlquery).addEntity(entityType).list();
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(String sqlquery, DBCoreLokMode lockMode) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		List<Map<String, Object>> entities=	currSession.createSQLQuery(sqlquery)
											.addEntity(entityType)
											.setLockMode("this", lockMode.getHibernateLock())
											.list();
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(Map<String, Object> params) {
		String queryString= "select * from " + entityType.getName();
		
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		SQLQuery query=currSession.createSQLQuery(queryString);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		List<Map<String, Object>> entities = query.addEntity(entityType).list();
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(Map<String, Object> params, DBCoreLokMode lockMode) {
		String queryString= "select * from " + entityType.getName();
		
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		SQLQuery query=currSession.createSQLQuery(queryString);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		List<Map<String, Object>> entities = query.addEntity(entityType)
											.setLockMode("this", lockMode.getHibernateLock())
											.list();
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getAll() {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		List<E> entities=  currSession.createQuery("select m from " + entityType.getName() + " m").list();
		return (List<D>) mapperObject.toDto(entities);		
	}

	@Override
	public void insert(D object) throws DataAccessException {
		E entity=(E) mapperObject.toEntity(object);
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		currSession.persist(entity);
	}

	@Override
	public void update(D object) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		E entity=(E) mapperObject.toEntity(object);
		currSession.merge(entity);
	}

	@Override
	public void delete(D object) throws DataAccessException {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		E entity=(E) mapperObject.toEntity(object);
		currSession.delete(entity);
	}
	
	@Override
	public void delete(Serializable object) throws DataAccessException {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		currSession.delete(getEntity(object));
	}

	@Override
	public int delete(Map<String, Object> params) {
		String queryString= "delete from " + entityType.getName();
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		SQLQuery query=currSession.createSQLQuery(queryString);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		int result= query.executeUpdate();
		return result;
	}
	
	private E getEntity(Serializable key) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return (E)currSession.get(entityType, key);
	}

}
