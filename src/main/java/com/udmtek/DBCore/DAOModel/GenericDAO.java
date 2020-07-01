package com.udmtek.DBCore.DAOModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
public interface GenericDAO <E extends DBCoreEntity,D extends DBCoreDTO, M extends DBCoreDTOMapper<?, ?>> {
	/**
	 * find an Entity of T from the database using primary keys
	 * @param
	 * @return T
	 */
	public D get(Serializable key);

	/**
	 * find all entities from the database
	 * @param
	 * @return List<T>
	 */
	public List<D> getAll();
	/**
	 * find all entities from the database using jpql query.
	 * @param
	 * @return List<T>
	 */
	public List<D> getfromJPQL(String jquery);
	/**
	 * find all entities from the database using sql query.
	 * @param
	 * @return List<T>
	 */
	public List<D> getfromSQL(String sqlquery);
	/**
	 * insert an entity to database. 
	 * @param T obejct
	 * @return void
	 */
	public void insert(D object);
	/**
	 * update an entity to database
	 * @param
	 * @return void
	 */
	public void save(D Object);
	/**
	 * delete an entity from database
	 * @param
	 * @return void
	 */
	public void delete(D object);
	/**
	 * delete an entity from database using primary key.
	 * @param
	 * @return void
	 */
	public void delete(Serializable key);
}

