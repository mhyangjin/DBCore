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

import org.hibernate.annotations.CreationTimestamp;

import com.udmtek.DBCore.DAOModel.EntityDAO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Entity
@Table(name="factory")
@IdClass(FactoryId.class)
//update SQL 생성 시 빈칼럼은 제거한다.
@org.hibernate.annotations.DynamicUpdate
//insert SQL 생성 시 빈칼럼은 제거한다.
@org.hibernate.annotations.DynamicInsert
@Getter
@Setter
@ToString
public class Factory implements EntityDAO{
	
	@Id	@Column(name="member_corpid")
	private String memberCorpid;
	
	@Id	@Column(name="factoryid")
	private String factoryid;
	
	@Column(name="biz_no")
	private String bizNo;
	
	@Column(name="factorytype")
	private String factorytype;
	
	@Column(name="fctry_nm")
	private String fctryNm;
	
	@Column(name="erpcode")
	private String erpcode;
	
	@Column(name="siteid")
	private String siteid;
	
	@Column(name="addr1")
	private String addr1;
	
	@Column(name="addr2")
	private String addr2;
	
	@Column(name="tph1")
	private String tph1;
	
	@Column(name="tph2")
	private String tph2;
	
	@Column(name="tph3")
	private String tph3;
	
	@Column(name="description")
	private String description;
	
	@Column(name="useyn")
	private Character useyn;

	@Column(name="createperson")
	private String createperson;
	
	@Column(name="createtime")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createtime;
	
	@Column(name="lasteventcomment")
	private String lasteventcomment;
	
	@Column(name="lasteventname")
	private String lasteventname;
	
	@Column(name="lasteventperson")
	private String lasteventperson;
	
	@Column(name="lasteventtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lasteventtime;
	
	@Column(name="lasteventtimekey")
	private String lasteventtimekey;
	
	@Column(name="cutoff_cd")
	private String cutoffCd;
	
	@Column(name="week_start_nm")
	private String weekStartNm;

	@Transient
	FactoryId Key=null;
	
	@Override
	public FactoryId getKey() {
		// TODO Auto-generated method stub
		if (Key == null)
		{
			Key=new FactoryId();
			if ( this.memberCorpid.length() > 0)
				Key.setMemberCorpid(this.memberCorpid);
			if ( this.factoryid.length() > 0 )
				Key.setFactoryid(this.factoryid);
		}
		return Key;
	}
	
}