package com.evolvus.abk.ftp.domain.mappers.temp;
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

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity 
@Table(name="FTP_CURVE_MAPPER_TEMP")
@ToString
public class FTPPolicyMapperTemp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CURV_MAP_TEMP_SEQ")
	@SequenceGenerator(name = "CURV_MAP_TEMP_SEQ", sequenceName = "CURV_MAP_TEMP_SEQ")
	@Column(name="C_ID")
	private Long id;
	
	@Column(name="FTP_CATEGORY")
	private String ftpCategory;
	
	@Column(name="CCY_CODE_IN")
	private String ccyCodeIn;
	
	@Column(name="CCY_CODE_NOT_IN")
	private String ccyCodeNotIn;
	
	@Column(name="DIVISION_CODE_IN")
	private String divisionCodeIn;
	
	@Column(name="DIVISION_CODE_NOT_IN")
	private String divisionCodeNotIn;
	
	@Column(name="ORIG_DIVISION_CODE_IN")
	private String origDivisionCodeIn;
	
	@Column(name="ORIG_DIVISION_CODE_NOT_IN")
	private String origDivisionCodeNotIn;
	
	@Column(name="CUST_TYPE_IN")
	private String custTypeIn;
	
	@Column(name="CUST_TYPE_NOT_IN")
	private String custTypeNotIn;
	
	@Column(name="SUBDIVISION_CODE_IN")
	private String subdivisionCodeIn;
	
	@Column(name="SUBDIVISION_CODE_NOT_IN")
	private String subdivisionCodeNotIn;

	@Column(name="FIXED_LENGTH")
	private String fixedLength;
	
	@Column(name="MATURITY_DATE")
	private String maturityDate;
	
	@Column(name="BASE_TENOR")
	private String baseTenor;
	
	@Column(name="MARGIN_TENOR")
	private String marginTenor;
	
	@Column(name="APPLICABLE_CURVE")
	private String applicableCurve;
	
	@Column(name="PRE_POST")
	private String prePost;
	
	@Column(name="FINAL_FTP_CATEGORY")
	private String finalFtpCategory;
	
	@Column(name="UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name="VERSION")
	private String version;
	
	@Column(name="UPLOADED_BY")
	private String uploadedBy;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	
}
