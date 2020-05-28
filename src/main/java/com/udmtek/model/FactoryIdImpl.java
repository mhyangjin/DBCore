package com.udmtek.model;

import java.util.ArrayList;
import java.util.List;

import com.udmtek.DBCore.DBAccessor.DBCoreKey;


@SuppressWarnings("serial")
public class FactoryIdImpl extends FactoryId implements DBCoreKey{
	
	public FactoryIdImpl (String memberCorpid, String  factoryid) {
		super();
		this.memberCorpid=memberCorpid;
		this.factoryid=factoryid;
	}

	@Override
	public List<String> getKeyColumns() {
		// TODO Auto-generated method stub
		List<String> columns=new ArrayList<String>();
		columns.add("memberCorpid");
		columns.add("factoryid");
		
		return columns;
	}

	@Override
	public boolean checkKeyData() {
		// TODO Auto-generated method stub
		return !(this.memberCorpid.isEmpty()||this.factoryid.isEmpty());
	}
}
