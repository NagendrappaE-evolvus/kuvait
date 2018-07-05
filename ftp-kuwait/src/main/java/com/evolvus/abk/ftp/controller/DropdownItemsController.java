package com.evolvus.abk.ftp.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.service.MapperVersionService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;

@RestController
@RequestMapping("items")
public class DropdownItemsController {

	private static final Logger LOG = LoggerFactory.getLogger(DropdownItemsController.class);


	@Autowired
	FtpAuditService ftpAuditService;
	
	@Autowired
	private MapperVersionService mapperVersionService; 

	
	@RequestMapping(value = "/findAllMappers", method = RequestMethod.GET)
	public ResponseEntity<CustomResponse> findAllMappers() {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start : getAllByFieldId");
		CustomResponse customResponse = new CustomResponse();
		customResponse.setDescription("Fetched.");
		try {
			customResponse.setData(mapperVersionService.listMappers());
		} catch (Exception e) {
			LOG.error("Error in fetching fields: "+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : getAllByFieldId");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}
}
