package com.evolvus.abk.ftp.service.impl;

import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.domain.mappers.archivals.FTPGrandMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPProductMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPGrandMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPProductMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPProductMapperTemp;

@Service
public class MapperConversionService {

	
	public FTPGrandMapper tempToMain(FTPGrandMapperTemp tempMapper) {
		FTPGrandMapper mainMapper = new FTPGrandMapper();
		
		mainMapper.setGlSubheadCode(tempMapper.getGlSubheadCode());
		mainMapper.setGlSubheadDesc(tempMapper.getGlSubheadDesc());
		
		mainMapper.setDrFtpCat(tempMapper.getDrFtpCat());
		mainMapper.setCrFtpCat(tempMapper.getCrFtpCat());
		
		mainMapper.setUserSubclassCodeIn(tempMapper.getUserSubclassCodeIn());
		mainMapper.setUserSubclassCodeNotIn(tempMapper.getUserSubclassCodeNotIn());
		
		mainMapper.setBAcidIn(tempMapper.getBAcidIn());
		mainMapper.setBAcidNotIn(tempMapper.getBAcidNotIn());
		
		mainMapper.setDivisionCodeIn(tempMapper.getDivisionCodeIn());
		mainMapper.setDivisionCodeNotIn(tempMapper.getDivisionCodeNotIn());
		
		mainMapper.setCustInLength(tempMapper.getCustInLength());
		mainMapper.setCustNotInLength(tempMapper.getCustNotInLength());
		
		mainMapper.setCustTypeIn(tempMapper.getCustTypeIn());
		mainMapper.setCustTypeNotIn(tempMapper.getCustTypeNotIn());
		
		mainMapper.setSubDivisionCodeIn(tempMapper.getSubDivisionCodeIn());
		mainMapper.setSubDivisionCodeNotIn(tempMapper.getSubDivisionCodeNotIn());
		
		mainMapper.setTradingBookNameIn(tempMapper.getTradingBookNameIn());
		mainMapper.setTradingBookNameNotIn(tempMapper.getTradingBookNameNotIn());
		
		mainMapper.setInstrumentClassIn(tempMapper.getInstrumentClassIn());
		mainMapper.setInstrumentClassNotIn(tempMapper.getInstrumentClassNotIn());
		
		mainMapper.setGroupByLogic(tempMapper.getGroupByLogic());
		mainMapper.setCount(tempMapper.getCount());
		
		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());
		
		mainMapper.setVersion(tempMapper.getVersion());
		mainMapper.setBankCode(tempMapper.getBankCode());
		
		return mainMapper;
	}
	
	public FTPGrandMapperArchive mainToArchive(FTPGrandMapper mainMapper) {
		FTPGrandMapperArchive archivalMapper = new FTPGrandMapperArchive();
		
		archivalMapper.setGlSubheadCode(mainMapper.getGlSubheadCode());
		archivalMapper.setGlSubheadDesc(mainMapper.getGlSubheadDesc());
		
		archivalMapper.setDrFtpCat(mainMapper.getDrFtpCat());
		archivalMapper.setCrFtpCat(mainMapper.getCrFtpCat());
		
		archivalMapper.setUserSubclassCodeIn(mainMapper.getUserSubclassCodeIn());
		archivalMapper.setUserSubclassCodeNotIn(mainMapper.getUserSubclassCodeNotIn());
		
		archivalMapper.setBAcidIn(mainMapper.getBAcidIn());
		archivalMapper.setBAcidNotIn(mainMapper.getBAcidNotIn());
		
		archivalMapper.setDivisionCodeIn(mainMapper.getDivisionCodeIn());
		archivalMapper.setDivisionCodeNotIn(mainMapper.getDivisionCodeNotIn());
		
		archivalMapper.setCustInLength(mainMapper.getCustInLength());
		archivalMapper.setCustNotInLength(mainMapper.getCustNotInLength());
		
		archivalMapper.setCustTypeIn(mainMapper.getCustTypeIn());
		archivalMapper.setCustTypeNotIn(mainMapper.getCustTypeNotIn());
		
		archivalMapper.setSubDivisionCodeIn(mainMapper.getSubDivisionCodeIn());
		archivalMapper.setSubDivisionCodeNotIn(mainMapper.getSubDivisionCodeNotIn());
		
		archivalMapper.setTradingBookNameIn(mainMapper.getTradingBookNameIn());
		archivalMapper.setTradingBookNameNotIn(mainMapper.getTradingBookNameNotIn());
		
		archivalMapper.setInstrumentClassIn(mainMapper.getInstrumentClassIn());
		archivalMapper.setInstrumentClassNotIn(mainMapper.getInstrumentClassNotIn());
		
		archivalMapper.setGroupByLogic(mainMapper.getGroupByLogic());
		archivalMapper.setCount(mainMapper.getCount());
		
		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());
		
		archivalMapper.setVersion(mainMapper.getVersion());
		
		archivalMapper.setBankCode(mainMapper.getBankCode());
		return archivalMapper;
	}
	
	public FTPProductMapper tempToMain(FTPProductMapperTemp tempMapper) {
		
		FTPProductMapper mainMapper = new FTPProductMapper();
		
		mainMapper.setFtpCategory(tempMapper.getFtpCategory());
		mainMapper.setProdCode(tempMapper.getProdCode());
		mainMapper.setProdDesc(tempMapper.getProdDesc());
		mainMapper.setAstLiabClas(tempMapper.getAstLiabClas());
		mainMapper.setCoreNonCore(tempMapper.getCoreNonCore());
		mainMapper.setCorePrnt(tempMapper.getCorePrnt());
		mainMapper.setBankCode(tempMapper.getBankCode());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());
		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setVersion(tempMapper.getVersion());
		return mainMapper;
		
	}
	
	public FTPProductMapperArchive mainToArchive(FTPProductMapper mainMapper) {
		
		FTPProductMapperArchive archivalMapper = new FTPProductMapperArchive();
		archivalMapper.setFtpCategory(mainMapper.getFtpCategory());
		archivalMapper.setProdCode(mainMapper.getProdCode());
		archivalMapper.setProdDesc(mainMapper.getProdDesc());
		archivalMapper.setAstLiabClas(mainMapper.getAstLiabClas());
		archivalMapper.setCoreNonCore(mainMapper.getCoreNonCore());
		archivalMapper.setCorePrnt(mainMapper.getCorePrnt());
		archivalMapper.setBankCode(mainMapper.getBankCode());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());
		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setVersion(mainMapper.getVersion());
		return archivalMapper;
		
	}
}
