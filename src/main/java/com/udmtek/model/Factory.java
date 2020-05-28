package com.udmtek.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="factory")
@IdClass(FactoryId.class)
public class Factory {
	
	@Id	@Column(name="factoryid")
	@Getter
	@Setter
	private String factoryid;
	
	@Id	@Column(name="member_corpid")
	@Getter
	@Setter
	private String memberCorpid;
	
	@Column(name="biz_no")
	@Getter
	@Setter
	private String bizNo;
	
	@Column(name="factorytype")
	@Getter
	@Setter
	private String factorytype;
	
	@Column(name="fctry_nm")
	@Getter
	@Setter
	private String fctryNm;
	
	@Column(name="erpcode")
	@Getter
	@Setter
	private String erpcode;
	
	@Column(name="siteid")
	@Getter
	@Setter
	private String siteid;
	
	@Column(name="addr1")
	@Getter
	@Setter
	private String addr1;
	
	@Column(name="addr2")
	@Getter
	@Setter
	private String addr2;
	
	@Column(name="tph1")
	@Getter
	@Setter
	private String tph1;
	
	@Column(name="tph2")
	@Getter
	@Setter
	private String tph2;
	
	@Column(name="tph3")
	@Getter
	@Setter
	private String tph3;
	
	@Column(name="description")
	@Getter
	@Setter
	private String description;
	
	@Column(name="useyn")
	@Getter
	@Setter
	private Character useyn;

	@Column(name="createperson")
	@Getter
	@Setter
	private String createperson;
	
	@Column(name="createtime")
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date createtime;
	
	@Column(name="lasteventcomment")
	@Getter
	@Setter
	private String lasteventcomment;
	
	@Column(name="lasteventname")
	@Getter
	@Setter
	private String lasteventname;
	
	@Column(name="lasteventperson")
	@Getter
	@Setter
	private String lasteventperson;
	
	@Column(name="lasteventtime")
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date lasteventtime;
	
	@Column(name="lasteventtimekey")
	@Getter
	@Setter
	private String lasteventtimekey;
	
	@Column(name="cutoff_cd")
	@Getter
	@Setter
	private String cutoffCd;
	
	@Column(name="week_start_nm")
	@Getter
	@Setter
	private String weekStartNm;
	
	@Transient
	@Getter
	@Setter
	private FactoryIdImpl factoryKey;
}