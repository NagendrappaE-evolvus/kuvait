package com.evolvus.abk.ftp.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity 
@Table(name="KWT_FTP_AUDIT")
@ToString
public class FtpAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TXN_AUD_SEQ")
	@SequenceGenerator(name = "TXN_AUD_SEQ", sequenceName = "TXN_AUD_SEQ")
	@Column(name="AUDIT_ID")
	private Long auditId;
	
	@Column(name="PRE_TXN_VALUE" , columnDefinition="VARCHAR(max) DEFAULT NULL")
	private String preTxnVal;
	
	@Column(name="POST_TXN_VALUE", columnDefinition="VARCHAR(max) DEFAULT NULL")
	private String postTxnVal;
	
	@Column(name="TXN_USER")
	private String txnUser;
	
	@Column(name="TXN_START_TIME")
	private Date txnStartTime;
	
	@Column(name="TXN_END_TIME")
	private Date txnEndTime;
	
	@Column(name="TXN_OBJECT_TYPE")
	private String txnObjectType;
	
	@Column(name="TXN_STATUS")
	private String txnStatus;
	
	@Column(name="TXN_ACTION")
	private String txnAction;
	
	@Column(name="STACK_TRACE", columnDefinition="VARCHAR(max) DEFAULT NULL") 
	private String stackTrace;
	
	@Column(name="MESSAGE")
	private String message;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
}
