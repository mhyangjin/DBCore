package com.udmtek.DBCore.model;

public class FactoryInfo extends FactoryDAO implements Cloneable {
	public FactoryInfo() {}
	
	public FactoryInfo (String memberCorpid, String factoryid) {
		this.setMemberCorpid(memberCorpid);
		this.setFactoryid(factoryid);
	}

}
