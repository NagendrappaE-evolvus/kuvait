package com.evolvus.abk.ftp.controller;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.FtpEntityService;
import com.evolvus.abk.ftp.service.impl.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	FtpEntityService ftpEntityService;

	@RequestMapping(value = "/users-paged", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getPagedUsers(@RequestBody Map<String, String> params, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getPagedUsers");
		CustomResponse customResponse = new CustomResponse();
		Integer currentPage = 0;
		Pageable pageData = null;
		Integer pageSize = 0;
		String bankCode = "";
		String username = "";
		try {
			if (params != null) {
				currentPage = Integer.parseInt(params.get("currentPage"));
				pageSize = Integer.parseInt(params.get("pageSize"));
				username = params.get("searchString") != null ? params.get("searchString") : "";
				bankCode = params.get("bankCode");
				pageData = userService.createPageRequest(currentPage, pageSize);
				customResponse.setData(userService.getPagedUsers(username, pageData,
						ftpEntityService.fetchEntityByCode(bankCode, Boolean.TRUE)));
				customResponse.setStatus(Constants.STATUS_OK);
			} else {
				throw new IllegalArgumentException();
			}

		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in paging Users.");
			LOG.error(customResponse.getDescription() + " => " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getPagedUsers");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> saveUser(@RequestBody User user, Principal currentUser) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start saveUser");
		CustomResponse customResponse = null;
		try {
			customResponse = userService.saveUser(user, ftpAuditService.getUserFromPrincipal(currentUser));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in saving user.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End saveUser");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> updateUser(@RequestBody User user, Principal currentUser) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start saveUser");
		CustomResponse customResponse = null;
		try {
			customResponse = userService.updateUser(user, ftpAuditService.getUserFromPrincipal(currentUser));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in saving user.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End saveUser");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/getbyname/{username}/{bankcode}", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getByCode(@PathVariable(name = "username") String username,
			@PathVariable(name = "bankcode") String bankCode) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getByCode => /getbyname/" + username);
		CustomResponse customResponse = new CustomResponse();
		User user = null;
		try {
			user = userService.findByUsername(username, ftpEntityService.fetchEntityByCode(bankCode, Boolean.TRUE));
			if (user != null) {
				customResponse.setData(user);
				customResponse.setStatus(Constants.STATUS_OK);
				customResponse.setDescription("User Fetched.");
			} else {
				customResponse.setStatus(Constants.STATUS_FAIL);
				customResponse.setDescription("User Does not Exist.");
			}
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in fetching user.");
			LOG.error(customResponse.getDescription() + " => " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getByCode => /getSchmMap/" + username);
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}
}
