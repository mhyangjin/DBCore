package com.codeJ.JPA.DAOModel;

import java.util.List;

/**
 * This is interface for the conversion of Entity and DTO
 * @author julu1
 * @param <E> subclass of GenericEntity
 * @param <D> subclass of GenericDTO
 */
public interface GenericDTOMapper <E extends GenericEntity, D extends GenericDTO>{
	/**
	 * convert to the subclass of GenericEntity from the subclass of GenericDTO
	 * @param subclass of GenericDTO
	 * @return subclass of GenericEntity
	 */
	public E toEntity(D dto);
	/**
	 * convert to the subclass of GenericDTO from the subclass of GenericEntity
	 * @param subclass of GenericEntity
	 * @return subclass of GenericDTO
	 */
	public D toDto (E entity);
	/**
	 * convert to the list of GenericEntity from the list of GenericDTO
	 * @param list of GenericDTO
	 * @return list of GenericEntity
	 */
	List<E> toEntity (List<D> dtos);
	/**
	 * convert to the list of GenericDTO from the list of GenericEntity
	 * @param list of GenericEntity
	 * @return list of GenericDTO
	 */
	List<D> toDto (List<E> entities);
}
