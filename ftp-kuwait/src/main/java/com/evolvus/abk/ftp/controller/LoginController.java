package com.evolvus.abk.ftp.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.service.impl.FtpEntityService;

@RestController
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	FtpEntityService ftpEntityService;
	
	@RequestMapping("/entities")
	public ResponseEntity<CustomResponse> getEntities() {
		LOG.debug("Start Get Enities =>active entities only");
		CustomResponse response = new CustomResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			response.setData(ftpEntityService.fetchAllIsActive(Boolean.TRUE));
			response.setDescription("Entities fetched.");
			response.setStatus(Constants.STATUS_OK);
		} catch(Exception e) {
			response.setDescription("Error in fetching Entities.");
			response.setStatus(Constants.STATUS_FAIL);
			LOG.error("Error in fetching entities => "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End Get Enities =>active entities only");
		return new ResponseEntity<CustomResponse>(response, httpStatus);
	}
}
