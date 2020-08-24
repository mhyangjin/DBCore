package com.udmtek.DBCore.DAOModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.udmtek.DBCore.ComException.DBTypeException;
import com.udmtek.DBCore.ComUtil.DBCoreLogger;

/**
 * Implementation  of the DBCoreDTOMapper
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 * @param <E> subclass of DBCoreEntity
 * @param <D> subclass of DBCoreDTO
 */
public class DBCoreDTOMapperImpl<E extends DBCoreEntity, D extends DBCoreDTO> implements DBCoreDTOMapper<E , D> {
	private Class<D> dtoType;
	private Class<E> entityType;
	
	/**
	 * constructor. 
	 * @param subclass type of DbCoreEntity
	 * @param subclass type of DBCoreDTO
	 */
	public DBCoreDTOMapperImpl(Class<E> entityType,Class<D> dtoType ) {
		super();
		this.dtoType = dtoType;
		this.entityType = entityType;
	}
	
	/**
	 * convert to the subclass of DBCoreEntity from the subclass of DBCoreDTO
	 * @param  subclass of DBCoreDTO
	 * @return subclass of DBCoreEntity
	 * @exception DBTypeException
	 */
	@Override
	public E toEntity(D dto) throws DBTypeException{
		E newobject = null;
		try {
			newobject =entityType.newInstance();
			List<Field> entityFileds= getFields(entityType);
			Map<String, Field> dtoFieldMap= getFieldsMap(dtoType);
			for ( Field field : entityFileds ) {
				String fieldName=field.getName();
				Field getfield=dtoFieldMap.get(fieldName);
				if (getfield == null ) continue;
				
				//get
				getfield.setAccessible(true);
				Object getObject= getfield.get(dto);
				//set
				field.setAccessible(true);
				field.set(newobject, getObject);
			}
			//convert  중 발생하는 Exception은 DBTypeException으로 throw 한다.
		} catch(InstantiationException | IllegalAccessException e) {
			DBCoreLogger.printDBError(this, e.getMessage());
			throw new DBTypeException(entityType.getSimpleName(),e.getMessage());
		}
		return newobject;
	}
	/**
	 * convert to the subclass of DBCoreDTO from the subclass of DBCoreEntity
	 * @param  subclass of DBCoreEntity
	 * @return subclass of DBCoreDTO
	 * @exception DBTypeException
	 */
	@Override
	public D toDto(E entity) throws DBTypeException{
		D newobject = null;
		try {
			newobject =dtoType.newInstance();
			List<Field> dtoFields= getFields(dtoType);
			Map<String, Field> entityFieldMap= getFieldsMap(entityType);

			for ( Field field : dtoFields ) {
				String fieldName=field.getName();
				Field getfield=entityFieldMap.get(fieldName);
				if (getfield == null ) continue;
				
				//get
				getfield.setAccessible(true);
				Object getObject= getfield.get(entity);
				//set
				field.setAccessible(true);
				field.set(newobject, getObject);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			DBCoreLogger.printDBError(this,"EXCEPTION:"+ e.getMessage());
			throw new DBTypeException(entityType.getSimpleName(),e.getMessage());
		}
		return newobject;
	}
	/**
	 * convert to the list of DBCoreEntity from the list of DBCoreDTO
	 * @param list of DBCoreDTO
	 * @return list of DBCoreEntity
	 * @exception DBTypeException
	 */
	@Override
	public List<E> toEntity(List<D> dtos) throws DBTypeException {
		List<E> entities = new ArrayList<>();
		for ( D dto:dtos) {
			entities.add(toEntity(dto));
		}
		return entities;
	}
	/**
	 * convert to the list of DBCoreDTO from the list of DBCoreEntity
	 * @param list of DBCoreEntity
	 * @return list of DBCoreDTO
	 * @exception DBTypeException
	 */
	@Override
	public List<D> toDto(List<E> entities)throws DBTypeException {
		DBCoreLogger.printTrace("toDto:" + entities.size());
		List<D> dtos = new ArrayList<>();
		for ( E entity:entities) {
			dtos.add(toDto( entity));
		}
		return dtos;
	}
	
	
	//상속관계인 모든 super class의 field를 찾는다.
	private <T> List<Field> getFields(Class<T> t) {
		List<Field> fields = new ArrayList<>();
		@SuppressWarnings("rawtypes")
		Class clazz=t;
		while (clazz != Object.class) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz=clazz.getSuperclass();
		}
		return fields;
	}
	
	//상속관계인 모든 super class의 field를 찾는다.	
	private <T> Map<String,Field> getFieldsMap(Class<T> t) {
		Map<String, Field> fieldsMap=new HashMap<>();
		@SuppressWarnings("rawtypes")
		Class clazz=t;
		while (clazz != Object.class) {
			Field[] thisfields=clazz.getDeclaredFields();
			for(Field field:thisfields) {
				fieldsMap.put(field.getName(), field);
			}
			clazz=clazz.getSuperclass();
		}
		return fieldsMap;
	}
}
