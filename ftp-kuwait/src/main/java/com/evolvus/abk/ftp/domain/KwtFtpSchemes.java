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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_SCHEMES")
public class KwtFtpSchemes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4564608231926517572L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_FTP_SCHM_SEQ")
	@SequenceGenerator(name = "KW_FTP_SCHM_SEQ", sequenceName = "KW_FTP_SCHM_SEQ")
	@Column(name = "SCHM_ID")
	private Long schemeId;
	
	@Column(name = "SCHM_CODE", length = 30)
	private String schmCode;

	@Column(name = "SCHM_DESC", length = 100)
	private String schmDesc;

	@Column(name = "GL_SUB_HEAD_CODE", length = 10)
	private String glSubHeadCode;
	
	@Column(name = "FTP_CATEGORY", length = 100)
	private String ftpCategory;
	
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

	@Override
	public String toString() {
		return String.format("KwtFtpSchemes={schmCode=%s,schmDesc=%s,glSubHeadCode=%s,ftpCategory=%s}", schmCode,
				schmDesc, glSubHeadCode,ftpCategory);
	}
}
