package com.evolvus.abk.ftp.service.impl;

import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.domain.mappers.archivals.FTPDivisionCodeMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPGrandMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPPolicyMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPProductMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPDivisionCodeMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPGrandMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPPolicyMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPProductMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPDivisionCodeMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPPolicyMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPProductMapperTemp;

@Service
public class MapperConversionService {

	// Category Mapper Converters

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

	// Product Mapper Converters

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

	// Policy Mapper Converter

	public FTPPolicyMapper tempToMain(FTPPolicyMapperTemp tempMapper) {
		FTPPolicyMapper mainMapper = new FTPPolicyMapper();

		mainMapper.setFtpCategory(tempMapper.getFtpCategory());
		mainMapper.setCcyCodeIn(tempMapper.getCcyCodeIn());
		mainMapper.setCcyCodeNotIn(tempMapper.getCcyCodeNotIn());

		mainMapper.setDivisionCodeIn(tempMapper.getDivisionCodeIn());
		mainMapper.setDivisionCodeNotIn(tempMapper.getDivisionCodeNotIn());

		mainMapper.setOrigDivisionCodeIn(tempMapper.getOrigDivisionCodeIn());
		mainMapper.setOrigDivisionCodeNotIn(tempMapper.getOrigDivisionCodeNotIn());

		mainMapper.setCustTypeIn(tempMapper.getCustTypeIn());
		mainMapper.setCustTypeNotIn(tempMapper.getCustTypeNotIn());

		mainMapper.setSubdivisionCodeIn(tempMapper.getSubdivisionCodeIn());
		mainMapper.setSubdivisionCodeNotIn(tempMapper.getSubdivisionCodeNotIn());

		mainMapper.setFixedLength(tempMapper.getFixedLength());
		mainMapper.setMaturityDate(tempMapper.getMaturityDate());

		mainMapper.setBaseTenor(tempMapper.getBaseTenor());
		mainMapper.setMarginTenor(tempMapper.getMarginTenor());

		mainMapper.setApplicableCurve(tempMapper.getApplicableCurve());
		mainMapper.setPrePost(tempMapper.getPrePost());

		mainMapper.setFinalFtpCategory(tempMapper.getFinalFtpCategory());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());

		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setBankCode(tempMapper.getBankCode());

		return mainMapper;
	}

	public FTPPolicyMapperArchive mainToArchive(FTPPolicyMapper mainMapper) {
		FTPPolicyMapperArchive archivalMapper = new FTPPolicyMapperArchive();
		archivalMapper.setFtpCategory(mainMapper.getFtpCategory());
		archivalMapper.setCcyCodeIn(mainMapper.getCcyCodeIn());
		archivalMapper.setCcyCodeNotIn(mainMapper.getCcyCodeNotIn());

		archivalMapper.setDivisionCodeIn(mainMapper.getDivisionCodeIn());
		archivalMapper.setDivisionCodeNotIn(mainMapper.getDivisionCodeNotIn());

		archivalMapper.setOrigDivisionCodeIn(mainMapper.getOrigDivisionCodeIn());
		archivalMapper.setOrigDivisionCodeNotIn(mainMapper.getOrigDivisionCodeNotIn());

		archivalMapper.setCustTypeIn(mainMapper.getCustTypeIn());
		archivalMapper.setCustTypeNotIn(mainMapper.getCustTypeNotIn());

		archivalMapper.setSubdivisionCodeIn(mainMapper.getSubdivisionCodeIn());
		archivalMapper.setSubdivisionCodeNotIn(mainMapper.getSubdivisionCodeNotIn());

		archivalMapper.setFixedLength(mainMapper.getFixedLength());
		archivalMapper.setMaturityDate(mainMapper.getMaturityDate());

		archivalMapper.setBaseTenor(mainMapper.getBaseTenor());
		archivalMapper.setMarginTenor(mainMapper.getMarginTenor());

		archivalMapper.setApplicableCurve(mainMapper.getApplicableCurve());
		archivalMapper.setPrePost(mainMapper.getPrePost());

		archivalMapper.setFinalFtpCategory(mainMapper.getFinalFtpCategory());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());

		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setBankCode(mainMapper.getBankCode());

		return archivalMapper;
	}

	// Division Code Mappers
	public FTPDivisionCodeMapper tempToMain(FTPDivisionCodeMapperTemp tempMapper) {

		FTPDivisionCodeMapper mainMapper = new FTPDivisionCodeMapper();

		mainMapper.setGlSubHeadCode(tempMapper.getGlSubHeadCode());

		mainMapper.setGlshChar(tempMapper.getGlshChar());
		mainMapper.setEntityCode(tempMapper.getEntityCode());

		mainMapper.setCategory(tempMapper.getCategory());
		mainMapper.setDivisionDesc(tempMapper.getDivisionDesc());

		mainMapper.setOfficer(tempMapper.getOfficer());
		mainMapper.setSubDivision(tempMapper.getSubDivision());

		mainMapper.setDivision(tempMapper.getDivision());
		mainMapper.setFinalDivisionDesc(tempMapper.getFinalDivisionDesc());

		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());

		mainMapper.setBankCode(tempMapper.getBankCode());

		return mainMapper;
	}

	public FTPDivisionCodeMapperArchive mainToArchive(FTPDivisionCodeMapper mainMapper) {
		FTPDivisionCodeMapperArchive archivalMapper = new FTPDivisionCodeMapperArchive();

		archivalMapper.setGlSubHeadCode(mainMapper.getGlSubHeadCode());

		archivalMapper.setGlshChar(mainMapper.getGlshChar());
		archivalMapper.setEntityCode(mainMapper.getEntityCode());

		archivalMapper.setCategory(mainMapper.getCategory());
		archivalMapper.setDivisionDesc(mainMapper.getDivisionDesc());

		archivalMapper.setOfficer(mainMapper.getOfficer());
		archivalMapper.setSubDivision(mainMapper.getSubDivision());

		archivalMapper.setDivision(mainMapper.getDivision());
		archivalMapper.setFinalDivisionDesc(mainMapper.getFinalDivisionDesc());

		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());

		archivalMapper.setBankCode(mainMapper.getBankCode());

		return archivalMapper;
	}
}
