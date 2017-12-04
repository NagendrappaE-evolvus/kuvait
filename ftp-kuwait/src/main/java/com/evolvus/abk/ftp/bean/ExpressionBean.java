package com.evolvus.abk.ftp.bean;

import java.util.List;
import lombok.Data;

@Data
public class ExpressionBean {
	private String expId; 
	private String expName; 
	private String expOpr;
	private List<String> expValues;
	private String expTargetType;
	private Integer crudOperation;
}
