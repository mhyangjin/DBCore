package com.udmtek.DBCore.DAOModel;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is super class for DTO need history field.
 * @author julu1
 * @version  0.2.0
 */
@Getter
@Setter
@ToString
public abstract class DBCoreDTOImpl implements DBCoreDTO {
	protected String createperson;
	protected Date createtime;
	protected String lasteventcomment;
	protected String lasteventname;
	protected String lasteventperson;
	protected Date lasteventtime;
	protected String lasteventtimekey;
	

}
