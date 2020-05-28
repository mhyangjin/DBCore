package com.udmtek.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.udmtek.DBCore.DBAccessor.DBCoreDAOJpa;
import com.udmtek.DBCore.DBAccessor.DBCoreKey;

@Repository
public class FactoryDAOImpl extends DBCoreDAOJpa<Factory> {
	public FactoryDAOImpl () {
		super(Factory.class);
	}
	
	public void update() {
//		this.save( this);
	}
}
