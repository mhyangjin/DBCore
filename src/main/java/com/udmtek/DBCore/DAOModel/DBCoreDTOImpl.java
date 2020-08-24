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
	protected String creatorId;
	protected Date createDateTime;
	protected String updatorId;
	protected Date updateDateTime;
}
