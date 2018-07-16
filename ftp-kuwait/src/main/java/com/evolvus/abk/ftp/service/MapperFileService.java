package com.evolvus.abk.ftp.service;

import java.security.Principal;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.domain.FtpEntity;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


public interface MapperFileService {
	
	CustomResponse uploadToTemp(FileInfo fileInfo, Principal user);
	
	void clearRecords(FtpEntity ftpEntity);
	
	Map<String,List<? extends Object>> getDifferenceOfTempAndMain(FtpEntity ftpEntity);
	
	Long archive(FtpEntity ftpEntity);
	
	Long insertToMain(FtpEntity ftpEntity);
	
	/*public CustomResponse uploadToMain(String fileType, FileInfo fileInfo, String date, User user);*/
}
