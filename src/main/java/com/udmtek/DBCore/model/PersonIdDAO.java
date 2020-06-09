package com.udmtek.DBCore.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class PersonIdDAO implements Serializable {
	protected String memberCorpid;
	protected String factoryidDft;
	protected String personid;
	
	public PersonIdDAO () {}
	public PersonIdDAO(String memberCorpid, String factoryidDft, String personid) {
		this.memberCorpid =memberCorpid;
		this.factoryidDft = factoryidDft;
		this.personid = personid;
	}
	@Override
	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null ) return false;
		if ( o.getClass() != this.getClass() ) return false;
		PersonIdDAO oKey=(PersonIdDAO) o;
		return  memberCorpid.equals(oKey.memberCorpid) 
				&&	factoryidDft.equals(oKey.factoryidDft)
				&&  personid.equals(oKey.personid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(memberCorpid,factoryidDft, personid);
	}
}
