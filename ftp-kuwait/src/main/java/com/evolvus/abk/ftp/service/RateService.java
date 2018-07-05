package com.evolvus.abk.ftp.service;


import java.util.Map;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.domain.User;


public interface RateService {

	public CustomResponse uploadRates(String fileType, FileInfo fileInfo, String date, User user, Boolean overwrite);
	
	default Map<String, Integer> getTenorBucketInDays(String tenor) {
		return null;
	}
	default String[] getSplittedStringArray(String str, String pattern) {
		return null;
	}
}
