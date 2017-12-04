package com.evolvus.abk.ftp.domain;

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
@Table(name = "KWT_FTP_RULE_EXPR")
public class RuleExpressions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KWT_FTP_EXP_SEQ")
	@SequenceGenerator(name = "KWT_FTP_EXP_SEQ", sequenceName = "KWT_FTP_EXP_SEQ")
	@Column(name = "EXP_ID")
	private Long expId;

	@Column(name = "EXP_NAME", length = 20)
	private String expName;

	@Column(name = "EXP_OPR", length = 10)
	private String expOpr;

	@Column(name = "EXP_VALUE", length = 30)
	private String expValue;

	@Column(name = "EXP_TARGET_TYPE", length = 30)
	private String expTargetType;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PROD_CODE", nullable = false)
	private FtpProduct product;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "RULE_ID", nullable = false)
	private RuleMappings ruleMap;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;

	@Override
	public String toString() {
		return String.format(
				"[RuleExpressions = {expId=%d,expName=%s,expOpr=%s,expValue=%s,expTargetType=%s,productCode=%s,ruleMap=%s-%s}]",
				expId, expName, expOpr, expValue, expTargetType, product.getProductCode(), ruleMap.getRuleName(),
				ruleMap.getRuleTarget());
	}
}
