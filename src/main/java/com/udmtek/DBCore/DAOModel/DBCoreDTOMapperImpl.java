package com.udmtek.DBCore.DAOModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class DBCoreDTOMapperImpl<E extends DBCoreEntity, D extends DBCoreDTO> implements DBCoreDTOMapper<E , D> {
	private Class<D> dtoType;
	private Class<E> entityType;
	public DBCoreDTOMapperImpl(Class<E> entityType,Class<D> dtoType ) {
		super();
		this.dtoType = dtoType;
		this.entityType = entityType;
	}
	
	@Override
	public E toEntity(D dto) {
		E newobject = null;
		try {
			newobject =entityType.newInstance();
			Field[] fields=entityType.getDeclaredFields();
			
			for ( Field field : fields ) {
				String fieldName=field.getName();
				try {
					dtoType.getDeclaredField(fieldName);
				}
				catch (NoSuchFieldException e) {continue;}
				String getterName = "get" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
				String setterName = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
				Object getObject=dtoType.getMethod(getterName).invoke(dto);
				entityType.getMethod(setterName,field.getType() ).invoke(newobject, getObject);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newobject;
	}

	@Override
	public D toDto(E entity) {
		D newobject = null;
		try {
			newobject =dtoType.newInstance();
			Field[] fields=dtoType.getDeclaredFields();
			
			for ( Field field : fields ) {
				String fieldName=field.getName();
				String getterName = "get" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
				String setterName = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
				Object getObject=entityType.getMethod(getterName).invoke(entity);
				dtoType.getMethod(setterName,field.getType() ).invoke(newobject, getObject);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newobject;
	}

	@Override
	public List<E> toEntity(List<D> dtos) {
		List<E> entities = new ArrayList<>();
		for ( D dto:dtos) {
			entities.add(toEntity(dto));
		}
		return entities;
	}

	@Override
	public List<D> toDto(List<E> entities) {
		List<D> dtos = new ArrayList<>();
		for ( E entity:entities) {
			dtos.add(toDto( entity));
		}
		return dtos;
	}
	
}
