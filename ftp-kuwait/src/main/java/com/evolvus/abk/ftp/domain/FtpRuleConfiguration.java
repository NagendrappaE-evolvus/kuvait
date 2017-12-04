package com.evolvus.abk.ftp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_RULE_CONFIG")
public class FtpRuleConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6865769018416612273L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_RULE_SEQ")
	@SequenceGenerator(name = "KW_RULE_SEQ", sequenceName = "KW_RULE_SEQ")
	@Column(name = "RULE_ID")
	private Long ruleId;

	@Column(name = "RULE_NAME", length = 30)
	private String ruleName;	
	
	@Column(name = "TRNSFR_MTHD", length = 30)
	private String transferMethod;

	@Column(name = "IS_CCY_CRV_APL")
	private Boolean isYieldCurveApplicable;
	
	@Column(name="YIELD_TENOR", length=30)
	private String yieldTenor;

	@Column(name = "IS_KEY_CRV_APL")
	private Boolean isKeyCurveApplicable;

	@Column(name = "KEY_TNR_COD", length = 30)
	private String keyRateCode;
	
	@Column(name = "IS_MRGN_ADJ_APL")
	private Boolean isMrgnAdjstTempApplicable;

	@Column(name = "MRGN_ADJ_TNR_COD", length = 30)
	private String mrgnAdjstTempCode;

	@Column(name = "IS_MRGN_EXT_APL")
	private Boolean isMrgnAdjstExtApplicable;

	@Column(name = "MRGN_EXT_TNR_COD", length = 30)
	private String mrgnAdjstExtCode;

	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleConfig", fetch = FetchType.LAZY)
	private List<ParameterConfiguration> params = new ArrayList<ParameterConfiguration>();

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_CODE", nullable = false)
	private FtpProduct product;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	

	@Override
	public String toString() {
		return String.format(
				"[RuleConfiguration={ruleId=%d,prodCode=%s,trnsfrMethod=%s,isYieldCurveApplicable=%s,isKeyCurveApplicable=%s,keyCurveCode=%s,isMrgnAdjApplicable=%s,mrgnAdjCurveCode=%s,isMrgnExtApplicable=%s,mrgnExtCurveCode=%s}]",
				ruleId, product.getProductCode(), transferMethod, isYieldCurveApplicable,
				isKeyCurveApplicable, keyRateCode, isMrgnAdjstTempApplicable, mrgnAdjstTempCode, isMrgnAdjstExtApplicable,
				mrgnAdjstExtCode);
	}
}
