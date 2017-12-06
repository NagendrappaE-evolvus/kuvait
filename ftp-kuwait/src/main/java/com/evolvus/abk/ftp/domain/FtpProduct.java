package com.evolvus.abk.ftp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_PRODUCT")
public class FtpProduct {

	@Id
	@Column(name = "PROD_CODE", length = 10)
	private String productCode;

	@Column(name = "PROD_NAME", length = 50)
	private String productName;

	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_UPDATED_BY", length = 20)
	private String lastUpdatedBy;

	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdatedDate;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
	private List<FtpRuleConfiguration> rules = new ArrayList<FtpRuleConfiguration>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductVariableFields> variableFields = new ArrayList<ProductVariableFields>();

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "KWT_FTP_PROD_SCHM_MAP", joinColumns = { @JoinColumn(name = "PROD_CODE") }, inverseJoinColumns = {
			@JoinColumn(name = "SCHM_CODE", referencedColumnName = "SCHM_CODE", table = "KWT_FTP_SCHEMES"),
			@JoinColumn(name = "GL_SUB_HEAD_CODE", referencedColumnName = "GL_SUB_HEAD_CODE", table = "KWT_FTP_SCHEMES"),
			@JoinColumn(name = "FTP_CATEGORY", referencedColumnName = "FTP_CATEGORY", table = "KWT_FTP_SCHEMES"),
			@JoinColumn(name = "BANK_CODE", referencedColumnName = "BANK_CODE", table = "KWT_FTP_SCHEMES") })
	private List<KwtFtpSchemes> schemes = new ArrayList<KwtFtpSchemes>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
	private List<RuleMappings> ruleMappings = new ArrayList<RuleMappings>();

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;

	@Override
	public String toString() {
		return String.format(
				"[Product={productCode=%s,productName=%s,createdBy=%s,createdTime=%s,updatedBy=%s,updatedTime=%s}]",
				productCode, productName, createdBy, createdDate, lastUpdatedBy, lastUpdatedDate);
	}
}
