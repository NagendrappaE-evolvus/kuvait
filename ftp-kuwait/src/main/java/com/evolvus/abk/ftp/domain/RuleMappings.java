package com.evolvus.abk.ftp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_RULE_MAPPINGS")
public class RuleMappings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KWT_FTP_RUL_MAP_SEQ")
	@SequenceGenerator(name = "KWT_FTP_RUL_MAP_SEQ", sequenceName = "KWT_FTP_RUL_MAP_SEQ")
	@Column(name="RULE_ID")
	private Long ruleId;
	
	@Column(name="RULE_TARGET")
	private String ruleTarget;
	
	@Column(name="RULE_NAME")
	private String ruleName;
	
	@Column(name="TARGET_TYPE")
	private String targetType;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleMap", fetch = FetchType.LAZY)
	private List<RuleExpressions> expressions = new ArrayList<RuleExpressions>();
	
	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY", length = 20)
	private String updatedBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PROD_CODE", nullable = false)
	private FtpProduct product;
	
	@Override
	public String toString() {
		return String.format("[Rule Mappings={ruleId=%d,ruleTarget=%s,productCode=%s,ruleName=%s,targetType=%s,createdBy=%s}]", ruleId,ruleTarget,product.getProductCode(),ruleName,targetType,createdBy);
	}
	
}
