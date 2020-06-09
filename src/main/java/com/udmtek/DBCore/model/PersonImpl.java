package com.udmtek.DBCore.model;

import com.udmtek.DBCore.DAOModel.GenericDAOImpl;

public class PersonImpl extends GenericDAOImpl<PersonInfo> {
	public PersonImpl () {
		super(PersonInfo.class);
	}
}
