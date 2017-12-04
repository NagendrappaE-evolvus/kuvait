package com.evolvus.abk.ftp.bean;

import java.util.List;

import com.evolvus.abk.ftp.domain.FtpRuleConfiguration;
import com.evolvus.abk.ftp.domain.ProductVariableFields;

import lombok.Data;

@Data
public class FtpProductBean {
	private String code;
	private String name;
	private FtpRuleConfiguration rule;
	private String rateRuleCalcMethd;
	private String adjCalcMethod;
	private List<ProductVariableFields> variableFields;
}
