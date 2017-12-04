package com.evolvus.abk.ftp.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "KWT_FTP_ITEM_MASTER")
public class ItemMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "KW_ITM_MSTR_SEQ")
	@SequenceGenerator(name = "KW_ITM_MSTR_SEQ", sequenceName = "KW_ITM_MSTR_SEQ")
	@Column(name = "ITM_ID")
	private Long itemId;
	
	@Column(name="ITEM_FLD_ID")
	private String itemFieldId;

	@Column(name = "ITM_CODE", length = 100)
	private String itemCode;

	@Column(name = "ITM_VAL", length = 100)
	private String itemValue;

	@Column(name = "ITM_GROUP", length = 100)
	private String itemGroup;

	@Column(name = "DISP_ORDER")
	private Integer displayOrder;

	@JsonIgnore
	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@JsonIgnore
	@Column(name = "LAST_UPDATED_BY", length = 20)
	private String updatedBy;

	@JsonIgnore
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@JsonIgnore
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_CODE")
	private FtpEntity bankCode;
	
	@Override
	public String toString() {
		return String.format(
				"[ItemMaster={itemId=%d, itemCode=%s, itemValue=%s, itemGroup=%s, displayOrder=%d, createdBy=%s, createdDate=%s, updatedBy=%s, updatedDate=%s}]",
				itemId, itemCode, itemValue, itemGroup, displayOrder, createdBy, createdDate, updatedBy, lastUpdatedDate);
	}
}
