package com.evolvus.abk.ftp.service.impl;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.commons.UserDetailsAdapter;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.FtpAuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FtpAuditService {
	private static final Logger LOG = LoggerFactory.getLogger(FtpAuditService.class);
	
	@Autowired
	FtpAuditRepository ftpAuditRepository;
	
	public String objectToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		LOG.debug("Start objectToJson.");
		String jsonString = Constants.EMPTY;
		try {
			 jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOG.error("Error in parsing object to JSON : "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End objectToJson.");
		return jsonString;
	}
	
	public void logAudit(FtpAudit audit) {
		LOG.debug("Start : logAudit");
		try {
			ftpAuditRepository.save(audit);
		} catch (Exception e) {
			LOG.error("Error in Logging Audit : "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : logAudit");
	}
	
	public void logAudit(List<FtpAudit> auditList) {
		LOG.debug("Start : logAudit");
		try {
			ftpAuditRepository.save(auditList);
		} catch (Exception e) {
			LOG.error("Error in Logging Audit : "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : logAudit");
	}
	
	public User getUserFromPrincipal(Principal user) {
		User userData = null;
		try {
			userData = ((UserDetailsAdapter) ((UsernamePasswordAuthenticationToken)user).getPrincipal()).getUser();
		} catch(NullPointerException exception) {
			userData = null;
			LOG.error("Error in Logging Audit : "+ExceptionUtils.getStackTrace(exception));
		}		
		return userData;
	}
	public Date getCurrentTime() {
		return new Date();
		
	}
}
