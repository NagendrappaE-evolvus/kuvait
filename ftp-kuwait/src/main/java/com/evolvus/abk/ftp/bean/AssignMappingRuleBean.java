package com.evolvus.abk.ftp.bean;

import java.util.List;

import lombok.Data;

@Data
public class AssignMappingRuleBean {
	private String modalTarget;
	private String productCode;
	private List<AssignRuleBean> ruleMappings;
}
