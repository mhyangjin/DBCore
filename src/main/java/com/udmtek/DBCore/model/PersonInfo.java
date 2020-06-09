package com.udmtek.DBCore.model;

public class PersonInfo extends PersonDAO implements Cloneable {
	public PersonInfo() {}
	
	public PersonInfo (String memberCorpid, String factoryidDft, String personid) {
		this.setMemberCorpid(memberCorpid);
		this.setFactoryidDft(factoryidDft);
		this.setPersonid(personid);
	}

}
