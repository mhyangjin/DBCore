package com.udmtek.DBCore.DAOModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.udmtek.DBCore.ComUtil.DBCoreLogger;

import lombok.Getter;
import lombok.ToString;

/**
 * This is super class of the Entity class needs history fields
 * @author julu1
 * @version v.0.2.0
 */
@ToString
@Getter
@MappedSuperclass
public abstract class DBCoreEntityImpl implements DBCoreEntity {

	@Column(name="createperson")
	protected String createperson;

	@Column(name="createtime")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createtime;

	@Column(name="lasteventcomment")
	protected String lasteventcomment;

	@Column(name="lasteventname")
	protected String lasteventname;

	@Column(name="lasteventperson")
	protected String lasteventperson;

	@Column(name="lasteventtime")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lasteventtime;

	@Column(name="lasteventtimekey")
	protected String lasteventtimekey;

	/**
	 * on update event, save event info.
	 */
	@PreUpdate
	public void onUpdate() {
		lasteventname="Update";
		lasteventtime=Timestamp.valueOf(LocalDateTime.now());
		lasteventtimekey= LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmssn")).substring(0, 20);
	}
	/**
	 * on insert event, save event info.
	 */
	@PrePersist
	public void onCreate() {
		lasteventtime=createtime;
		lasteventtimekey= LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmssn")).substring(0, 20);
		lasteventname="Create";
		lasteventperson=createperson;
	}
}
