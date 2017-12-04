package com.evolvus.abk.ftp.domain;

import java.io.Serializable;

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

@Data
@Entity
@Table(name="KWT_FTP_PRM_CNF")
public class ParameterConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4039507746965952275L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_PARAM_ID_SEQ")
	@SequenceGenerator(name = "KW_PARAM_ID_SEQ", sequenceName = "KW_PARAM_ID_SEQ")
	@Column(name = "PARAM_ID")
	private Long paramId;
	
	@Column(name="KEY_NAME",length=20)
	private String keyName;
	
	@Column(name="PARAM_VAL",length=100)
	private String paramVal;
	
	@Column(name="OPERATOR",length=10)
	private String operatorValue;
	
	@Column(name="PROD_CODE",length=20)
	private String productCode;
	
	@ManyToOne
	@JoinColumn(name="RULE_ID", nullable=false)
	@JsonIgnore
	private FtpRuleConfiguration ruleConfig;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	
	@Override
	public String toString() {
		return String.format("[ParameterConfiguration={paramId=%d,keyName=%s,paramVal=%s,productCode=%s,ruleConfig=%s}]", paramId,keyName,paramVal,productCode,ruleConfig.toString());
	}
}
