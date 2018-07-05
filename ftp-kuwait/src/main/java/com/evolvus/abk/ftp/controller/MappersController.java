package com.evolvus.abk.ftp.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.service.MapperFileService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;

@RestController
@RequestMapping("mappers")
public class MappersController {

	private static final Logger LOG = LoggerFactory.getLogger(MappersController.class);

	@Autowired(required = true)
	@Qualifier(value = "GrandMapperFileService")
	private MapperFileService grandMapperService;

	@Autowired(required = true)
	@Qualifier(value = "ProductMapperFileService")
	private MapperFileService productMapperService;

	@Autowired(required = true)
	@Qualifier(value = "PolicyMapperFileService")
	private MapperFileService policyMapperService;
	
	@Autowired(required = true)
	@Qualifier(value = "DivisionCodeMapperService")
	MapperFileService divisionMapperService;
	
	@Autowired
	FtpAuditService ftpAuditService;

	/**
	 * Process Mappers.
	 *
	 * param1 mapperName
	 */
	@RequestMapping(value = "/process-mappers", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> processMapper(@RequestParam("mapperName") String mapperName, Principal user) {
		LOG.debug("Start process mapper");
		HttpStatus httpStatus = HttpStatus.OK;
		CustomResponse customResponse = new CustomResponse();
		User appUser=ftpAuditService.getUserFromPrincipal(user);
		FtpEntity ftpEntity = appUser.getEntity();
		try {
			if ("CT".equals(mapperName)) {
				grandMapperService.archive(ftpEntity);
				Long mapperRecords = grandMapperService.insertToMain(ftpEntity);
				if (mapperRecords > 0L) {
					customResponse.setData("{mapperRecords:" + mapperRecords + "}");
					customResponse.setStatus("OK");
					customResponse.setDescription("Data saved successfully");
				}
			}
			if ("PD".equals(mapperName)) {
				productMapperService.archive(ftpEntity);
				Long mapperRecords = productMapperService.insertToMain(ftpEntity);
				if (mapperRecords > 0L) {
					customResponse.setData("{mapperRecords:" + mapperRecords + "}");
					customResponse.setStatus("OK");
					customResponse.setDescription("Data saved successfully");
				}
			}
			if ("PC".equals(mapperName)) {
				policyMapperService.archive(ftpEntity);
				Long mapperRecords = policyMapperService.insertToMain(ftpEntity);
				if (mapperRecords > 0L) {
					customResponse.setData("{mapperRecords:" + mapperRecords + "}");
					customResponse.setStatus("OK");
					customResponse.setDescription("Data saved successfully");
				}
			}
			if ("DC".equals(mapperName)) {
				divisionMapperService.archive(ftpEntity);
				Long mapperRecords = divisionMapperService.insertToMain(ftpEntity);
				if (mapperRecords > 0L) {
					customResponse.setData("{mapperRecords:" + mapperRecords + "}");
					customResponse.setStatus("OK");
					customResponse.setDescription("Data saved successfully");
				}
			}
		} catch (Exception e) {
			LOG.error("ERROR In loading data");
		}
		return new ResponseEntity<>(customResponse, httpStatus);
	}
}
