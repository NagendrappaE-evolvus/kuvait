package com.evolvus.abk.ftp.bean;

import java.util.List;

import lombok.Data;

@Data
public class AssignRuleBean {
	
	private String ruleId;
	private String ruleTarget;
	private String ruleName;
	private String targetType;
	private Integer crudOperation;
	
	private List<ExpressionBean> expressions;
}
