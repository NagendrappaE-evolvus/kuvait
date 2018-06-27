package com.evolvus.abk.ftp.service;

import java.security.Principal;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import java.util.List;
import java.util.Map;

public interface MapperFileService {
	
	CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user);
	
	void clearRecords();
	
	Map<String,List<? extends Object>> getDifferenceOfTempAndMain();
	
	/*public CustomResponse uploadToMain(String fileType, FileInfo fileInfo, String date, User user);*/
}
