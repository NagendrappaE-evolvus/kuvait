package com.evolvus.abk.ftp.domain.rates;

import java.math.BigDecimal;
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
@Table(name="FTP_KEY_RATES")
@ToString
public class KeyRates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KEY_RATE_SEQ")
	@SequenceGenerator(name = "KEY_RATE_SEQ", sequenceName = "KEY_RATE_SEQ")
	@Column(name="KEY_ID")
	private Long id;
	
	@Column(name="BUSINESS_CLOSE_DATE")
	private java.sql.Date businessCloseDate;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="TENOR")
	private String tenor;
	
	@Column(name="KEY_RATE",columnDefinition="decimal(17,7)")
	private BigDecimal keyRate;
	
	@Column(name="LATEST")
	private String latest;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	
	@Column(name="UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name="UPLOADED_BY")
	private String uploadedBy;
	
	@Column(name="OVERWRITTEN_DATE")
	private Date overwrittenDate;
	
	@Column(name="OVERWRITTEN_BY")
	private String overwrittenBy;
	
}
