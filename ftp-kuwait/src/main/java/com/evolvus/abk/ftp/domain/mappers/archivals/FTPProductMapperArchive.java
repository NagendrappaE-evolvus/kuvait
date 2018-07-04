package com.evolvus.abk.ftp.domain.mappers.archivals;

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
@Table(name="FTP_PRODUCT_MAPPER_ARCHIVE")
@ToString
public class FTPProductMapperArchive {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="PROD_MAP_ARCH_SEQ")
	@SequenceGenerator(name="PROD_MAP_ARCH_SEQ",sequenceName="PROD_MAP_ARCH_SEQ")
	@Column(name="PROD_ID")
	private Long id;
	
	@Column(name="FTP_CATEGORY", length=255)
	private String ftpCategory;
	
	@Column(name="PROD_CODE", length=255)
	private String prodCode;
	
	@Column(name="PROD_DESC", length=255)
	private String prodDesc;

	@Column(name="AST_LIAB_CLAS", length=255)
	private String astLiabClas;
	
	@Column(name="Core_Non_Core", length=255)
	private String coreNonCore;

	@Column(name="CORE_PRNT", length=255)
	private String 	corePrnt;
	
	@Column(name="UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name="UPLOADED_BY")
	private String uploadedBy;
	
	@Column(name="VERSION", length=255)
	private String version;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BANK_CODE")
	private FtpEntity bankCode;
}
