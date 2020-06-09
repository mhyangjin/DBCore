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
public class FactoryIdDAO implements Serializable {
	protected String memberCorpid;
	protected String factoryid;
	
	public FactoryIdDAO () {}
	public FactoryIdDAO(String memberCorpid, String factoryid) {
		this.memberCorpid =memberCorpid;
		this.factoryid = factoryid;
	}
	@Override
	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null ) return false;
		if ( o.getClass() != this.getClass() ) return false;
		FactoryIdDAO oFactoryID=(FactoryIdDAO) o;
		return  memberCorpid.equals(oFactoryID.memberCorpid) &&	factoryid.equals(oFactoryID.factoryid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(memberCorpid,factoryid);
	}
}
