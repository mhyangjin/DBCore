package com.udmtek.DBCoreTest.model;

import com.udmtek.DBCore.DAOModel.GenericDAOImpl;

public class PersonImpl extends GenericDAOImpl<PersonInfo> {
	public PersonImpl () {
		super(PersonInfo.class);
	}
}
