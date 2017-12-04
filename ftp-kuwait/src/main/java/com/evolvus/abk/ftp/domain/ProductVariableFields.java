package com.evolvus.abk.ftp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_VRBL_FIELDS")
public class ProductVariableFields implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7234935419335825338L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_VRBL_FLD_SEQ")
	@SequenceGenerator(name = "KW_VRBL_FLD_SEQ", sequenceName = "KW_VRBL_FLD_SEQ")
	@Column(name = "AUTO_GEN_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_CODE")
	@JsonIgnore
	private FtpProduct product;

	@Column(name = "KEY_NAME", length = 20)
	private String keyName;

	@Column(name = "KEY_DESC", length = 30)
	private String keyDesc;

	@Column(name = "FIELD_ID", length = 50)
	private String fieldId;

	@Column(name = "FIELD_NAME", length = 50)
	private String fieldName;

	@Column(name = "DATA_VLDN_PTTRN", length = 50)
	private String dataValidationPattern;

	@Column(name = "FIELD_ORDER")
	private Integer fieldOrder;

	@Column(name = "FIELD_TYPE")
	private String fieldType; //TEXT,NUMBER,DATE,MULTISELECT,AUTOCOMPLETE,SELECT,PRECHIPS,CHIPS
	
	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "LAST_UPDATED_BY", length = 20)
	private String updatedBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	
	@Transient
	private List<ItemMaster> itemList = new ArrayList<ItemMaster>();
	
	@Transient
	private List<ItemMaster> listValue = new ArrayList<ItemMaster>();
	
	@Transient
	private String textValue;
	
	@Transient
	private String dateValue;
	
	@Transient
	private String operator;
	
	

	@Override
	public String toString() {
		return String.format(
				"[ProductVariableFields={id=%d,ProductCode=%s,keyName=%s,keyDesc=%s,fieldId=%s,fieldName=%s,dataValidationPattern=%s,fieldOrder=%d,fieldType=%s}]",
				id, product.getProductCode(), keyName, keyDesc, fieldId, fieldName, dataValidationPattern, fieldOrder,
				fieldType);
	}
}
