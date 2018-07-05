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
@Table(name="FTP_CURRENCY_RATES")
@ToString
public class CurrencyRates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CUR_RATE_SEQ")
	@SequenceGenerator(name = "CUR_RATE_SEQ", sequenceName = "CUR_RATE_SEQ")
	@Column(name="CUR_ID")
	private Long id;
	
	@Column(name="BUSINESS_CLOSE_DATE")
	private java.sql.Date businessCloseDate;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="TENOR")
	private String tenor;
	
	@Column(name="DAYS_FROM")
	private Integer daysFrom;
	
	@Column(name="DAYS_TO")
	private Integer daysTo;
	
	@Column(name="BASE", columnDefinition="decimal(17,7)")
	private BigDecimal base;
	
	@Column(name="MARGIN", columnDefinition="decimal(17,7)")
	private BigDecimal margin;
	
	@Column(name="NET", columnDefinition="decimal(17,7)")
	private BigDecimal net;
	
	@Column(name="UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name="UPLOADED_BY")
	private String uploadedBy;
	
	@Column(name="OVERWRITTEN_DATE")
	private Date overwrittenDate;
	
	@Column(name="OVERWRITTEN_BY")
	private String overwrittenBy;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	private FtpEntity bankCode;
}
