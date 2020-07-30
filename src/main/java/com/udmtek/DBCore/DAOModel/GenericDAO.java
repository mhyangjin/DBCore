package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.udmtek.DBCore.DBAccessor.DBCoreLokMode;

/**
 * This is Interface for DAO
 * @author julu1 <julu1 @ naver.com >
 * @param sub class of DBCoreEntity
 * @param sub class of DBCoreDTO
 * @param sub class of DBCoreDTOMapper
 * @version 0.1.0
 */
public interface GenericDAO <E extends DBCoreEntity,D extends DBCoreDTO, M extends DBCoreDTOMapper<?, ?>> {
	/**
	 * find from the database using primary keys
	 * @param DBCoreEntityKey 
	 * @return DTO object
	 */
	public D get(DBCoreEntityKey key);
	/**
	 * find, and lock a shared Rock mode on Entity of T from the database using primary keys
	 * @param DBCoreEntityKey 
	 * @param DBCoreLokMode
	 * @return DTO object
	 */
	public D get(DBCoreEntityKey key, DBCoreLokMode lockMode);
	/**
	 * find all entities from the database
	 * @return List of DTO object
	 */
	public List<D> getAll();
	/**
	 * find all entities from the database using jpql query.
	 * @param String
	 * @return List of DTO object
	 */
	public List<D> getfromJPQL(String jquery);
	/**
	 * find all entities from the database using sql query.
	 * @param String
	 * @return List of DTO object
	 */
	public List<D> getfromSQL(String sqlquery);
	/**
	 * find, and lock  a shared Rock mode on all entities from the database using sql query.
	 * @param String query
	 * @param DBCoreLokMode
	 * @return List of DTO object
	 */
	public List<D> getfromSQL(String sqlquery,DBCoreLokMode lockMode);
	/**
	 * find from the database using parameter
	 * @param map <"columnName", "value">
	 * @return List of DTO object
	 */
	public List<D> getfromSQL(Map<String, Object> params);
	/**
	 * find from the database using parameter and lock  a shared Rock mode 
	 * @param map <"columnName", "value">
	 * @param DBCoreLokMode
	 * @return List of DTO object
	 */
	public  List<D> getfromSQL(Map<String, Object> params ,DBCoreLokMode lockMode);
	/**
	 * insert an entity to database. 
	 * @param DTO object
	 */
	public void insert(D object);
	/**
	 * update an entity to database
	 * @param DTO object
	 */
	public void update(D Object);
	/**
	 * delete an entity from database
	 * @param DTO object
	 */
	public void delete(D object);
	/**
	 * delete an entity from database using primary key.
	 * @param DBCoreEntityKey
	 */
	public void delete(DBCoreEntityKey key);
	/**
	 * delete an entity from database using primary key params.
	 * @param map <"columnName", "value">
	 * @return The number of entities deleted
	 */
	public int delete(Map<String, Object> params);
}



