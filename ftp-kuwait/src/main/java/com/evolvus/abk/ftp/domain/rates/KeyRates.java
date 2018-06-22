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
	private Date businessCloseDate;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="TENOR")
	private String tenor;
	
	@Column(name="KEY_RATE")
	private BigDecimal keyRate;
	
	@Column(name="LATEST")
	private String latest;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
}
