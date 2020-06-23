package com.udmtek.DBCoreTest.model;

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
import com.udmtek.DBCore.DAOModel.EntityImpl;
import com.udmtek.DBCore.DAOModel.EntityKeyImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author julu1 <julu1 @ naver.com >
 * @version 0.1.0
 */
@Entity
@Table(name="person")
@IdClass(PersonDAO.Key.class)
//update SQL 생성 시 빈칼럼은 제거한다.
@org.hibernate.annotations.DynamicUpdate
//insert SQL 생성 시 빈칼럼은 제거한다.
@org.hibernate.annotations.DynamicInsert
@Getter
@Setter
@ToString
public class PersonDAO extends EntityImpl{
	
	@Id	@Column(name="member_corpid")
	private String memberCorpid;
	
	@Id	@Column(name="factoryid_dft")
	private String factoryidDft;
	
	@Id	@Column(name="personid")
	private String personid;
	
	@Column(name="personname")
	private String personname;
	
	@Column(name="personclassid")
	private String personclassid;
	
	@Column(name="password")
	private String password;
	
	@Column(name="usr_flag")
	private String usrFlag;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="customerid")
	private String customerid;
	
	@Column(name="deptcode")
	private String deptcode;
	
	@Column(name="email")
	private String email;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="jobtitle")
	private String jobtitle;
	
	@Column(name="job_position_cd")
	private String jobPosisionCd;
	
	@Column(name="job_assn_cd")
	private String jobAssnCd;
	
	@Column(name="upper_person_id")
	private String upperPersonId;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="nation_cd")
	private String nationCd;
	
	@Column(name="join_date")
	@Temporal(TemporalType.DATE)
	private Date joinDate;
	
	@Column(name="leave_date")
	@Temporal(TemporalType.DATE)
	private Date leaveDate;
	
	@Column(name="use_yn")
	private String userYn;
	
	@Column(name="sys_yn")
	private String sysYn;
	
	@Column(name="pw_change_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pwChageTime;
	
	@Column(name="createperson")
	private String createperson;
	
	@Column(name="createtime")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createtime;
	
	@Column(name="lasteventperson")
	private String lasteventperson;
	
	@Column(name="lasteventtime")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date lasteventtime;
	
	@Column(name="lasteventcomment")
	private String lasteventcomment;
	
	@Column(name="lasteventname")
	private String lasteventname;
	
	@Column(name="lasteventtimekey")
	private String lasteventtimekey;
	
	@Column(name="deptname")
	private String deptname;
	
	@Column(name="email2")
	private String email2;
	
	@Column(name="tph2")
	private String tph2;
	
	@Column(name="tph3")
	private String tph3;
	

	@Transient
	private Key key;
	
	public PersonDAO () {}
	
	public PersonDAO (String memberCorpid,String factoryidDft, String personid) {
		if ( key == null)
			key = new Key(memberCorpid, factoryidDft,personid);
	}
	
	public Key getKey()
	{
		return key;
	}
	
	public Key getKey(String memberCorpid,String factoryidDft, String personid)
	{
		if ( key == null)
			key = new Key(memberCorpid, factoryidDft,personid);
		return key;
	}
	

	public static class Key extends EntityKeyImpl {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Getter
		@Setter
		private String memberCorpid;
		
		@Getter
		@Setter
		private String factoryidDft;
		
		@Getter
		@Setter
		private String personid;
	
		public Key() {
			super (Key.class);
		}
		
		public Key(String memberCorpid, String factoryidDft,String personid) {
			super (Key.class);
			this.memberCorpid = memberCorpid;
			this.factoryidDft = factoryidDft;
			this.personid = personid;
		}
	}
}
