package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.udmtek.DBCore.DBAccessor.DBCoreLokMode;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface GenericDAO <E extends DBCoreEntity,D extends DBCoreDTO, M extends DBCoreDTOMapper<?, ?>> {
	/**
	 * find, and lock a shared Rock mode on Entity of T from the database using primary keys
	 * @param key 
	 * @return DTO object
	 */
	public D get(Serializable key);
	/**
	 * find, and lock a shared Rock mode on Entity of T from the database using primary keys
	 * @param key 
	 * @return DTO object
	 */
	public D get(Serializable key, DBCoreLokMode lockMode);
	/**
	 * find all entities from the database
	 * @param
	 * @return List<DTO object>
	 */
	public List<D> getAll();
	/**
	 * find all entities from the database using jpql query.
	 * @param
	 * @return List<DTO object>
	 */
	public List<D> getfromJPQL(String jquery);
	/**
	 * find all entities from the database using sql query.
	 * @param
	 * @return List<DTO object>
	 */
	public List<D> getfromSQL(String sqlquery);
	/**
	 * find all entities from the database using sql query.
	 * @param
	 * @return List<DTO object>
	 */
	public List<D> getfromSQL(String sqlquery,DBCoreLokMode lockMode);

	/**
	 * find, and lock  a shared Rock mode on Entity of T from the database using primary keys
	 * @param ey params. <"columnName", "value">
	 * @return DTO object
	 */
	public List<D> getfromSQL(Map<String, Object> params);
	/**
	 * find an Entity of T from the database using primary keys
	 * @param key params. <"columnName", "value">
	 * @return DTO object
	 */
	public  List<D> getfromSQL(Map<String, Object> params ,DBCoreLokMode lockMode);
	

	
	/**
	 * insert an entity to database. 
	 * @param DTO object
	 * @return void
	 */
	public void insert(D object);
	/**
	 * update an entity to database
	 * @param DTO object
	 * @return void
	 */
	public void update(D Object);
	/**
	 * delete an entity from database
	 * @param DTO object
	 * @return void
	 */
	public void delete(D object);
	/**
	 * delete an entity from database using primary key.
	 * @param key
	 * @return void
	 */
	public void delete(Serializable key);
	
	/**
	 * delete an entity from database using primary key params.
	 * @param key params. <"columnName", "value">
	 * @return The number of entities deleted
	 */
	public int delete(Map<String, Object> params);
}



