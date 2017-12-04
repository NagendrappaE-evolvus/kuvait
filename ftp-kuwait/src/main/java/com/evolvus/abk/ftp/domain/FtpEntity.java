package com.evolvus.abk.ftp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="KWT_FTP_ENTITY")
@Data
public class FtpEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1365559768098015670L;

	@Id
	@Column(name="BANK_CODE", length=2)
	private String bankCode;
	
	@Column(name="REGION_NAME", length=100)
	private String regionName;
	
	@Column(name="IS_ACTIVE") 
	private Boolean isActive;
}
