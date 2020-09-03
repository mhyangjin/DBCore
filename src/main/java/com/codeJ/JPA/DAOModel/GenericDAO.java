package com.codeJ.JPA.DAOModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.codeJ.JPA.DBAccessor.DBLokMode;

/**
 * This is Interface for DAO
 * @author julu1 <julu1 @ naver.com >
 * @param sub class of GenericEntity
 * @param sub class of GenericDTO
 * @param sub class of GenericDTOMapper
 * @version 0.1.0
 */
public interface GenericDAO <E extends GenericEntity,D extends GenericDTO, M extends GenericDTOMapper<?, ?>> {
	/**
	 * find from the database using primary keys
	 * @param long key Value 
	 * @return DTO object
	 */
	public D get(long keyValue);
	/**
	 * find from the database using primary keys
	 * @param String key Value 
	 * @return DTO object
	 */
	public D get(String keyValue);
	/**
	 * find from the database using primary keys
	 * @param int key Value 
	 * @return DTO object
	 */
	public D get(int keyValue);
	/**
	 * find from the database using primary keys
	 * @param GenericEntityKey 
	 * @return DTO object
	 */
	public D get(GenericEntityKey key);
	/**
	 * find, and lock a shared Rock mode on Entity of T from the database using primary keys
	 * @param GenericEntityKey 
	 * @param DBLokMode
	 * @return DTO object
	 */
	public D get(GenericEntityKey key, DBLokMode lockMode);
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
	 * @param DBLokMode
	 * @return List of DTO object
	 */
	public List<D> getfromSQL(String sqlquery,DBLokMode lockMode);
	/**
	 * find from the database using parameter
	 * @param map <"columnName", "value">
	 * @return List of DTO object
	 */
	public List<D> getfromSQL(Map<String, Object> params);
	/**
	 * find from the database using parameter and lock  a shared Rock mode 
	 * @param map <"columnName", "value">
	 * @param DBLokMode
	 * @return List of DTO object
	 */
	public  List<D> getfromSQL(Map<String, Object> params ,DBLokMode lockMode);
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
	 * @param GenericEntityKey
	 */
	public void delete(GenericEntityKey key);
	/**
	 * delete an entity from database using primary key params.
	 * @param map <"columnName", "value">
	 * @return The number of entities deleted
	 */
	public int delete(Map<String, Object> params);
	/**
	 * delete an entity from database using primary key.
	 * @param String Key
	 */
	public void delete(String keyValue);

	/**
	 * delete an entity from database using primary key.
	 * @param int key
	 */
	public void delete(int keyValue);
	/**
	 * delete an entity from database using primary key.
	 * @param long key
	 */
	public void delete(long keyValue);
}



