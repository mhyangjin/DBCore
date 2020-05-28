package com.udmtek.model;

import java.io.Serializable;
import java.util.Objects;


@SuppressWarnings("serial")
public class FactoryId implements Serializable {
	protected String memberCorpid;
	protected String factoryid;
	
	public FactoryId () {}
	
	@Override
	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null ) return false;
		if ( o.getClass() != this.getClass() ) return false;
		FactoryId oFactoryID=(FactoryId) o;
		return  memberCorpid.equals(oFactoryID.memberCorpid) &&	factoryid.equals(oFactoryID.factoryid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(memberCorpid,factoryid);
	}
}
