package com.evolvus.abk.ftp.service;


import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.domain.User;


public interface RateService {

	public CustomResponse uploadRates(String fileType, FileInfo fileInfo, String date, User user, Boolean overwrite);
}
