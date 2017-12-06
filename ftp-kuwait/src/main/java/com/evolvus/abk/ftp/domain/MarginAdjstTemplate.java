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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_MRGN_ADJST_RATES")
public class MarginAdjstTemplate implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_ADJ_TMP_RTE_SEQ")
	@SequenceGenerator(name = "KW_ADJ_TMP_RTE_SEQ", sequenceName = "KW_ADJ_TMP_RTE_SEQ")
	@Column(name = "AUTO_GEN_ID")
	private Long mgnAdjId;
	
	@Column(name = "RT_TYPE", length = 20)
	private String rateType;
	
	@Column(name = "CCY_CODE", length = 3)
	private String ccyCode;
	
	@Column(name = "RT_APP_DT")
	private Date rateAppDate;

	@Column(name = "RT_APP_FILE_DT")
	private Date rateDateInFile;

	@Column(name = "TNR_CODE", length = 30)
	private String tenorCode;

	@Column(name = "TNR_DSC", length = 100)
	private String tenorDesc;

	@Column(name = "FRM_TNR")
	private Integer tenorFrom;

	@Column(name = "TO_TNR")
	private Integer tenorTo;

	@Column(name = "FRM_TNR_UNT",length=6)
	private String tenorFromUnit;

	@Column(name = "TO_TNR_UNT",length=6)
	private String tenorToUnit;

	@Column(name = "MRGN_ADJST_RATE", columnDefinition="decimal(17,7)") 
	private BigDecimal mrgnAdjRate;

	@Column(name = "UPLOAD_DATE")
	private java.util.Date uploadDate;

	@Column(name = "UPLOAD_BY", length = 15)
	private String uploadedBy;
	
	@Column(name="IS_OVERWRITE")
	private Boolean isOverwritten;
	
	@Column(name="OVERWRITE_BY", length = 15)
	private String overwrittenBy;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;

	@Override
	public String toString() {
		return String.format(
				"[MarginAdjstTemp={keyRateId=%d, rateType=%s, ccyCode=%s, rateAppDate=%s, rateAppDateInFile=%s, tenorCode=%s, tenorDesc=%s, tenorFrom=%d,tenorTo=%d,tenorFromUnit=%s,tenorToUnit=%s,mrgnAdjRate=%f,uploadDate=%s,uploadedBy=%s}]",
				mgnAdjId, rateType, ccyCode, rateAppDate, rateDateInFile, tenorCode, tenorDesc, tenorFrom, tenorTo,
				tenorFromUnit, tenorToUnit, mrgnAdjRate, uploadDate, uploadedBy);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}  

}
