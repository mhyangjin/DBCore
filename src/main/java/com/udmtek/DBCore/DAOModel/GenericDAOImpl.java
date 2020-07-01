package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
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
	
	@Override
	public D get(Serializable key) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		DBCoreEntity result;
		result = currSession.get(entityType, key);
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
	public void save(D object) {
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
	
	private E getEntity(Serializable key) {
		Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
		return (E)currSession.get(entityType, key);
	}
}
