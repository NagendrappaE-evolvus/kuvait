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
@Table(name="FTP_FINAL_DIVISION_MAPPER_TEMP")
@ToString
public class FTPDivisionCodeMapperTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "DIV_CODE_TMP_SEQ")
	@SequenceGenerator(name = "DIV_CODE_TMP_SEQ", sequenceName = "DIV_CODE_TMP_SEQ")
	@Column(name="D_ID")
	private Long id;
	
	@Column(name="GL_SUB_HEAD_CODE",length=20)
	private String glSubHeadCode;
	
	@Column(name="GLSH_CHAR")
	private Integer glshChar;
	
	@Column(name="ENTITY_CODE")
	private String entityCode;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="DIVISION_DESC")
	private String divisionDesc;
	
	@Column(name="OFFICER")
	private String officer;

	@Column(name="SUBDIVISION_IN",length=255)
	private String subDivisionIn;
	
	@Column(name="SUBDIVISION_NOT_IN",length=255)
	private String subDivisionNotIn;
	
	@Column(name="DIVISION",length=10)
	private String division;

	@Column(name="FINAL_DIVISION_DESC")
	private String finalDivisionDesc;
	
	@Column(name="FTP_DIVISION_CODE" ,length=20)
	private String ftpDivisionCode;
	
	@Column(name="UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name="VERSION")
	private String version;
	
	@Column(name="UPLOADED_BY")
	private String uploadedBy;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	private FtpEntity bankCode;
}