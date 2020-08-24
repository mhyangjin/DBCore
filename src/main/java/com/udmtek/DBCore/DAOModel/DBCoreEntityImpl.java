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

	@Column(name="creator_id")
	protected String creatorId;

	@Column(name="create_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createDataTime;

	@Column(name="updator_id")
	protected String updatorId;

	@Column(name="update_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updateDateTime;

	/**
	 * on update event, save event info.
	 */
	@PreUpdate
	public void onUpdate() {
		updateDateTime=Timestamp.valueOf(LocalDateTime.now());
	}
	/**
	 * on insert event, save event info.
	 */
	@PrePersist
	public void onCreate() {
		createDataTime=Timestamp.valueOf(LocalDateTime.now());
	}
}
