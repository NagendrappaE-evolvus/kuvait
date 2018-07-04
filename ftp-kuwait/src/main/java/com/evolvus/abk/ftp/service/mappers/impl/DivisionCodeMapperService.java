package com.evolvus.abk.ftp.service.mappers.impl;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.service.MapperFileService;

public class DivisionCodeMapperService implements MapperFileService {

	@Override
	public CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearRecords() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long archive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long insertToMain() {
		// TODO Auto-generated method stub
		return null;
	}

}
