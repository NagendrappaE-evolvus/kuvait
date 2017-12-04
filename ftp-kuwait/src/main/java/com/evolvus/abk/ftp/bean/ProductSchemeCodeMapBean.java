package com.evolvus.abk.ftp.bean;

import java.util.List;

import com.evolvus.abk.ftp.domain.KwtFtpSchemes;

import lombok.Data;

@Data
public class ProductSchemeCodeMapBean {
	private String productCode;
	private List<KwtFtpSchemes> prodSchemes;
}
