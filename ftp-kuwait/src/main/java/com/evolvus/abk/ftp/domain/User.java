package com.evolvus.abk.ftp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.evolvus.abk.ftp.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="KWT_FTP_USERS")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ID_SEQ")
	@SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ")
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="EMAIL", length=50)
	private String email;
	
	@Column(name="USER_NAME", length=50)
	private String username;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="FULL_NAME")
	private String fullName;
	
	@Column(name="LAST_LOGIN_TIME")
	private Date lastLoginTime;
	
	@Column(name="IS_ACTIVE")
	private Boolean isActive;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="IS_ADMIN")
	private Boolean isAdmin;
	
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate;
	
	@JsonIgnore
	@Transient
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity entity;

	public User() {
		email = username = name = fullName = Constants.EMPTY;
	}
	
	@Override
	public String toString() {
		return String.format("[User={email:%s,username:%s,name:%s,fullName:%s,password:******}]", email,username,name,fullName);
	}
}
