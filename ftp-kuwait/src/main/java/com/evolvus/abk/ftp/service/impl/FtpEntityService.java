package com.evolvus.abk.ftp.service.impl;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.repository.FtpEntityRepository;

@Service
public class FtpEntityService {
	
	private final static Logger LOG = LoggerFactory.getLogger(FtpEntityService.class);
			
	@Autowired
	FtpEntityRepository ftpEntityRepository;
	
	public FtpEntity fetchEntityByCode(String entityCode,Boolean isActive) {
		FtpEntity entity = null;
		LOG.debug("Start fetchEntityByCode");
		try { 
			entity = ftpEntityRepository.findByBankCodeAndIsActive(entityCode,isActive);
		} catch(Exception e) {
			LOG.error("Error in fetchEntityByCode "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End fetchEntityByCode");
		return entity;
	}
	
	public List<FtpEntity> fetchAllIsActive(Boolean isActive) {
		List<FtpEntity> entities = null;
		LOG.debug("Start fetchEntityByCode");
		try { 
			entities = ftpEntityRepository.findAllByIsActive(isActive);
		} catch(Exception e) {
			LOG.error("Error in fetchEntityByCode "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End fetchEntityByCode");
		return entities;
	}
}
