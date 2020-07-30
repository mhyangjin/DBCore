package com.udmtek.DBCore.DAOModel;

import java.util.List;

/**
 * This is interface for the conversion of Entity and DTO
 * @author julu1
 * @param <E> subclass of DBCoreEntity
 * @param <D> subclass of DBCoreDTO
 */
public interface DBCoreDTOMapper <E extends DBCoreEntity, D extends DBCoreDTO>{
	/**
	 * convert to the subclass of DBCoreEntity from the subclass of DBCoreDTO
	 * @param subclass of DBCoreDTO
	 * @return subclass of DBCoreEntity
	 */
	public E toEntity(D dto);
	/**
	 * convert to the subclass of DBCoreDTO from the subclass of DBCoreEntity
	 * @param subclass of DBCoreEntity
	 * @return subclass of DBCoreDTO
	 */
	public D toDto (E entity);
	/**
	 * convert to the list of DBCoreEntity from the list of DBCoreDTO
	 * @param list of DBCoreDTO
	 * @return list of DBCoreEntity
	 */
	List<E> toEntity (List<D> dtos);
	/**
	 * convert to the list of DBCoreDTO from the list of DBCoreEntity
	 * @param list of DBCoreEntity
	 * @return list of DBCoreDTO
	 */
	List<D> toDto (List<E> entities);
}
