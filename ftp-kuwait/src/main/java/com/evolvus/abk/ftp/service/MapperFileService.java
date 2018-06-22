package com.evolvus.abk.ftp.service;

import java.security.Principal;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;

public interface MapperFileService {
	
	public CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user);
	
	/*public CustomResponse uploadToMain(String fileType, FileInfo fileInfo, String date, User user);*/
}
