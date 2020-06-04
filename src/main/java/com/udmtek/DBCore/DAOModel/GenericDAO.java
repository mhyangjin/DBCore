package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface GenericDAO <T extends EntityDAO> {
	/**
	 * find an Entity of T from the database using primary keys
	 * @param
	 * @return T
	 */
	public T get(Serializable key);
	/**
	 * make primary key class from T
	 * @param
	 * @return Serializable
	 */
	public Serializable getKey(T object);
	/**
	 * create, and return DAOImpl of primary key of specified table using tableName
	 * @param
	 * @return GenericKeyDAOImpl<?>
	 */
	public GenericKeyDAOImpl<?> getKeyDAOImpl(String tableName);
	/**
	 * find all entities from the database
	 * @param
	 * @return List<T>
	 */
	public List<T> getAll();
	/**
	 * find all entities from the database using jpql query.
	 * @param
	 * @return List<T>
	 */
	public List<T> getfromJPQL(String jquery);
	/**
	 * find all entities from the database using sql query.
	 * @param
	 * @return List<T>
	 */
	public List<T> getfromSQL(String sqlquery);
	/**
	 * insert an entity to database. 
	 * @param T obejct
	 * @return void
	 */
	public void insert(T object);
	/**
	 * update an entity to database
	 * @param
	 * @return void
	 */
	public void save(T Object);
	/**
	 * delete an entity from database
	 * @param
	 * @return void
	 */
	public void delete(T object);
	/**
	 * delete an entity from database using primary key.
	 * @param
	 * @return void
	 */
	public void delete(Serializable key);
}

