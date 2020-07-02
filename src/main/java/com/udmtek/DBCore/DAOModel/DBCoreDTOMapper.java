package com.udmtek.DBCore.DAOModel;

import java.util.List;

public interface DBCoreDTOMapper <E extends DBCoreEntity, D extends DBCoreDTO>{
	public E toEntity(D dto);
	public D toDto (E entity);
	List<E> toEntity (List<D> dtos);
	List<D> toDto (List<E> entities);
}
