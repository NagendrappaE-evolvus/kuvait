package com.evolvus.abk.ftp.domain;

import java.math.BigDecimal;
import java.sql.Date;

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

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_CCY_RATES")
public class CurrencyCurveRates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_CCY_RTE_SEQ")
	@SequenceGenerator(name = "KW_CCY_RTE_SEQ", sequenceName = "KW_CCY_RTE_SEQ")
	@Column(name = "AUTO_GEN_ID")
	private Long baseRateId;

	@Column(name = "CCY_CODE", length = 3)
	private String ccyCode;

	@Column(name = "RT_APP_DT")
	private Date rateAppDate;

	@Column(name = "RT_APP_FILE_DT")
	private Date rateDateInFile;

	@Column(name = "TNR_CODE", length = 20)
	private String tenorCode;

	@Column(name = "TNR_DSC", length = 50)
	private String tenorDesc;

	@Column(name = "DAYS_FROM")
	private Integer tenorFromDay;

	@Column(name = "DAYS_TO")
	private Integer tenorToDay;

	@Column(name = "MONTH_FROM")
	private Integer tenorFromMonth;

	@Column(name = "MONTH_TO")
	private Integer tenorToMonth;

	@Column(name = "BASE_RATE", columnDefinition="decimal(17,7)") 
	private BigDecimal baseRate;
	
	@Column(name="KEY_RT_REF")
	private String keyTnrCodeRef;

	@Column(name = "UPLOAD_DATE")
	private java.util.Date uploadDate;

	@Column(name = "UPLOAD_BY", length = 15)
	private String uploadedBy;
	
	@Column(name="IS_OVERWRITE")
	private Boolean isOverwritten;
	
	@Column(name="OVERWRITE_BY", length = 15)
	private String overwrittenBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;

	@Override
	public String toString() {
		return String.format(
				"[CurrencyCurveRates={baseRateId=%d,ccyCode=%s,rateAppDate=%s,rateAppDateInFile=%s,tenorCode=%s,tenorDesc=%s,tenorFromDay=%d,tenorToDay=%d,tenorFromMonth=%d,tenorToMonth=%d,baseRate=%f,uploadDate=%s,uploadedBy=%s}]",
				baseRateId, ccyCode, rateAppDate, rateDateInFile, tenorCode, tenorDesc, tenorFromDay, tenorToDay,
				tenorFromMonth, tenorToMonth, baseRate, uploadDate, uploadedBy);
	}
}
