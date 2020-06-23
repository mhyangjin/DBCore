package com.udmtek.DBCoreTest.model;

public class PersonInfo extends PersonDAO {
	public PersonInfo() {}
	
	public PersonInfo (String memberCorpid, String factoryidDft, String personid) {
		this.setMemberCorpid(memberCorpid);
		this.setFactoryidDft(factoryidDft);
		this.setPersonid(personid);
	}

}
