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
import com.evolvus.abk.ftp.domain.mappers.TopMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity 
@Table(name="FTP_GRAND_MAPPER_ARCHIVE")
@ToString
public class FTPGrandMapperArchive {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GRND_MAP_ARCH_SEQ")
	@SequenceGenerator(name = "GRND_MAP_ARCH_SEQ", sequenceName = "GRND_MAP_ARCH_SEQ")
	@Column(name="G_ID")
	private Long gId;
	
	@Column(name="GL_SUBHEAD_CODE" ,length=10)
	private String glSubheadCode;
	
	@Column(name="GL_SUBHEAD_DESC", length=255)
	private String glSubheadDesc;
	
	@Column(name="DR_FTP_CATEGORY", length=255)
	private String drFtpCat;
	
	@Column(name="CR_FTP_CATEGORY", length=255)
	private String crFtpCat;
	
	@Column(name="USER_SUBCLASS_CODE_IN", length=255)
	private String userSubclassCodeIn;
	
	@Column(name="USER_SUBCLASS_CODE_NOT_IN", length=255)
	private String userSubclassCodeNotIn;
	
	@Column(name="BACID_IN", length=255)
	private String bAcidIn;
	
	@Column(name="BACID_NOT_IN", length=255)
	private String bAcidNotIn;
	
	@Column(name="DIVISION_CODE_IN", length=5)
	private String divisionCodeIn;
	
	@Column(name="DIVISION_CODE_NOT_IN", length=255)
	private String divisionCodeNotIn;
	
	@Column(name="CUST_IN_LENGTH", length=2)
	private String custInLength;
	
	@Column(name="CUST_TYPE_IN", length=255)
	private String custTypeIn;
	
	@Column(name="CUST_NOTIN_LENGTH", length=2)
	private String custNotInLength;
	
	@Column(name="CUST_TYPE_NOT_IN", length=255)
	private String custTypeNotIn;
	
	@Column(name="SUBDIVISION_CODE_IN", length=255)
	private String subDivisionCodeIn;
	
	@Column(name="SUBDIVISION_CODE_NOT_IN", length=255)
	private String subDivisionCodeNotIn;
	
	@Column(name="TRADING_BOOK_NAME_IN", length=255)
	private String tradingBookNameIn;
	
	@Column(name="TRADING_BOOK_NAME_NOT_IN", length=255)
	private String tradingBookNameNotIn;
	
	@Column(name="INSTRUMENT_CLASS_IN", length=255)
	private String instrumentClassIn;

	@Column(name="INSTRUMENT_CLASS_NOT_IN", length=255)
	private String instrumentClassNotIn;
	
	@Column(name="GROUP_BY_LOGIC", length=255)
	private String groupByLogic;
	
	@Column(name="UPLOADED_BY")
	private String uploadedBy;
	
	@Column(name="COUNT")
	private Double count;
	
	@Column(name="UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name="VERSION", length=255)
	private String version;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
}

