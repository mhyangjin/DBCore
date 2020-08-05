package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.udmtek.DBCore.ComException.DBAccessException;
import com.udmtek.DBCore.ComException.DBTypeException;
import com.udmtek.DBCore.ComException.InvalidNullableException;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;
import com.udmtek.DBCore.DBAccessor.DBCoreLokMode;
import com.udmtek.DBCore.DBAccessor.DBCoreSessionManager;

/**
 * Implementation of GenericDAO
 * @author julu1 <julu1 @ naver.com >
 * @param sub class of DBCoreEntity
 * @param sub class of DBCoreDTO
 * @param sub class of DBCoreDTOMapper
 * @version 0.1.0
 */
public class GenericDAOImpl<E extends DBCoreEntity,D extends DBCoreDTO, M extends DBCoreDTOMapper<E,D>>
							implements GenericDAO<E,D,M> {
	private Class<E> entityType;
	private Class<D> dtoType;
	private M mapperObject;
	
	@Autowired
	ApplicationContext context;
	
	/**
	 * constructor
	 * @param subclass type that inherits DBCoreEntity
	 * @param subclass type that inherits DBCoreDTO
	 */
	public GenericDAOImpl(Class<E> entityType, Class<D> dtoType) {
		super();
		this.entityType = entityType;
		this.dtoType = dtoType;
	}
	
	/**
	 * set MapperObject
	 * @param An implementation that inherits DBCoreDTOMapper
	 */
	@Autowired
	public void setMapperObject(M mapperObject) {
		this.mapperObject = mapperObject;
	}
	/**
	 * find from the database using primary keys
	 * @param DBCoreEntityKey 
	 * @return DTO object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public D get(DBCoreEntityKey key) throws DBAccessException,DBTypeException,InvalidNullableException {
		if (!key.isValid() )
			;  //exception throw
		DBCoreEntity result=null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			result = currSession.get(entityType, key);
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( result == null) return null;
		return (D) mapperObject.toDto((E)result);
	}
	/**
	 * find, and lock a shared Rock mode on Entity of T from the database using primary keys
	 * @param DBCoreEntityKey 
	 * @return DTO object
	 * @exception DBAccessException, DBTypeException, InvalidNullableException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public D get(DBCoreEntityKey key, DBCoreLokMode lockMode) throws DBAccessException,DBTypeException,InvalidNullableException{
		if (!key.isValid() )
			;  //exception throw
		DBCoreEntity result=null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			result = currSession.get(entityType, key,lockMode.getHibernateLock());
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( result == null) return null;
		return (D) mapperObject.toDto((E)result);
	}
	/**
	 * find all entities from the database using jpql query.
	 * @param String
	 * @return List of DTO object
	 * @exception DBAccessException,DBTypeException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromJPQL(String Hquery)  throws DBAccessException,DBTypeException {
		List<E> entities=null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			entities=currSession.createQuery(Hquery).list();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( entities == null) return null;
		return (List<D>) mapperObject.toDto(entities);
	}
	/**
	 * find all entities from the database using sql query.
	 * @param String
	 * @return List of DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(String sqlquery) throws DBAccessException,DBTypeException {
		List<Map<String, Object>> entities= null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			entities= currSession.createSQLQuery(sqlquery).addEntity(entityType).list();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( entities == null) return null;
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}
	/**
	 * find, and lock  a shared Rock mode on all entities from the database using sql query.
	 * @param String query
	 * @param DBCoreLokMode
	 * @return List of DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(String sqlquery, DBCoreLokMode lockMode) throws DBAccessException,DBTypeException {
		List<Map<String, Object>> entities=null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			entities=	currSession.createSQLQuery(sqlquery)
											.addEntity(entityType)
											.setLockMode("this", lockMode.getHibernateLock())
											.list();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( entities == null) return null;
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}
	/**
	 * find from the database using parameter
	 * @param map <"columnName", "value">
	 * @return List of DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(Map<String, Object> params) throws DBAccessException,DBTypeException {
		String queryString= "select * from " + entityType.getName();
		List<Map<String, Object>> entities=null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			SQLQuery query=currSession.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			entities = query.addEntity(entityType).list();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( entities == null) return null;
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}
	/**
	 * find from the database using parameter and lock  a shared Rock mode 
	 * @param map <"columnName", "value">
	 * @param DBCoreLokMode
	 * @return List of DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getfromSQL(Map<String, Object> params, DBCoreLokMode lockMode) throws DBAccessException,DBTypeException {
		String queryString= "select * from " + entityType.getName();
		List<Map<String, Object>> entities=null;
		try {
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			SQLQuery query=currSession.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			entities = query.addEntity(entityType)
						.setLockMode("this", lockMode.getHibernateLock())
						.list();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( entities == null) return null;
		return (List<D>) mapperObject.toDto((List<E>)entities);
	}
	/**
	 * find all entities from the database
	 * @return List of DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<D> getAll() throws DBAccessException,DBTypeException  {
		List<E> entities=  null;
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			entities=  currSession.createQuery("select m from " + entityType.getName() + " m").list();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		if ( entities == null) return null;
		return (List<D>) mapperObject.toDto(entities);		
	}
	/**
	 * insert an entity to database. 
	 * @param DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@Override
	public void insert(D object) throws DataAccessException,DBTypeException{
		E entity=(E) mapperObject.toEntity(object);
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			currSession.persist(entity);
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
	}
	/**
	 * update an entity to database
	 * @param DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@Override
	public void update(D object) throws DataAccessException,DBTypeException {
		E entity=(E) mapperObject.toEntity(object);
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			currSession.merge(entity);
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
	}
	/**
	 * delete an entity from database
	 * @param DTO object
	 * @exception DBAccessException,DBTypeException 
	 */
	@Override
	public void delete(D object) throws DataAccessException ,DBTypeException{
		E entity=(E) mapperObject.toEntity(object);
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			currSession.delete(entity);
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
	}
	/**
	 * delete an entity from database using primary key.
	 * @param DBCoreEntityKey
	 * @exception DBAccessException,DBTypeException 
	 */
	@Override
	public void delete(DBCoreEntityKey key) throws DataAccessException, InvalidNullableException {
		if (!key.isValid() )
			;  //exception throw
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			currSession.delete(getEntity(key));
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
	}
	/**
	 * delete an entity from database using primary key params.
	 * @param map <"columnName", "value">
	 * @return The number of entities deleted
	 * @exception DBAccessException,DBTypeException 
	 */
	@Override
	public int delete(Map<String, Object> params) throws DataAccessException{
		String queryString= "delete from " + entityType.getName();
		int result=0;
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			SQLQuery query=currSession.createSQLQuery(queryString);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			result= query.executeUpdate();
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
		return result;
	}
	
	private E getEntity(DBCoreEntityKey key) throws DataAccessException {
		try {	
			Session currSession= DBCoreSessionManager.getCurrentSession().getThisSession();
			return (E)currSession.get(entityType, key);
		} catch (Exception e) {
			DBCoreLogger.printDBError(e.getMessage());
			throw new DBAccessException(entityType.getClass().getSimpleName() , e.getMessage());
		}
	}


}
