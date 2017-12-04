package com.evolvus.abk.ftp.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SchemeCodeBean {
	private Long schemeId;
	private String schemeCode;
	private String schemeDesc;
	private String glSubHeadCode;
	private String ftpCategory;
}
