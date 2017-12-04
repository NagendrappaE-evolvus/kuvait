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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FtpProductBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.FtpRuleConfigService;
import com.evolvus.abk.ftp.service.impl.ProductService;

@RestController
@RequestMapping("rules")
public class FtpRuleController {

	private static final Logger LOG = LoggerFactory.getLogger(FtpRuleController.class);

	@Autowired
	FtpRuleConfigService ruleConfigService;

	@Autowired
	ProductService productService;

	@Autowired
	FtpAuditService ftpAuditService;

	@RequestMapping(value = "/paged-rules", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getPagedRules(@RequestBody Map<String, String> params, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		CustomResponse response = new CustomResponse();
		LOG.debug("Start : getPagedRules");
		Integer currentPage = 0;
		Pageable pageData = null;
		Integer pageSize = 0;
		String searchString = "";
		FtpProduct product = null;
		User currentUser = null;
		try {
			if (params != null) {
				currentPage = Integer.parseInt(params.get("currentPage"));
				pageSize = Integer.parseInt(params.get("pageSize"));
				searchString = params.get("searchString") != null ? params.get("searchString") : "";
				pageData = ruleConfigService.createPageRequest(currentPage, pageSize);
				currentUser = ftpAuditService.getUserFromPrincipal(user);
				product = productService.getProductByCode(params.get("productCode").toString(),currentUser);
				response.setData(ruleConfigService.getPagedRules(product, searchString, pageData, currentUser));
			}
			
		} catch (NullPointerException e) {
			LOG.error("Error in fetching rules=>" + ExceptionUtils.getStackTrace(e));
		} catch (Exception e) {
			LOG.error("Error in fetching rules=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : getPagedRules");
		return new ResponseEntity<CustomResponse>(response, httpStatus);
	}

	@RequestMapping(value = "/save-rule", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> saveFtpRule(@RequestBody FtpProductBean product, Principal user) {
		CustomResponse response = null;
		LOG.debug("Start : saveFtpRule");
		try {
			response = ruleConfigService.saveRules(product, ftpAuditService.getUserFromPrincipal(user));
		} catch (Exception e) {
			response = new CustomResponse();
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error in saving rule.");
			LOG.error("Error in saving rule =>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : saveFtpRule");
		return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-rule", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getFtpRule(@RequestBody Map<String, String> params, Principal user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start getFtpRule");
		try {
			if (params.get("ruleId") != null && !Constants.EMPTY.equals(params.get("ruleId").toString())) {
				Long rule = Long.parseLong(params.get("ruleId").toString());
				response.setData(ruleConfigService.findRuleById(rule, ftpAuditService.getUserFromPrincipal(user)));
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Illegal Parameters.");
			}
		} catch (Exception e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Illegal Parameters.");
			LOG.error("Error in fetching rule"+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getFtpRule");
		return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-rule", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> deleteFtpRule(@RequestBody Map<String, String> params, Principal user) {
		CustomResponse response = null;
		LOG.debug("Start deleteFtpRule");
		try {
			response = ruleConfigService.deleteRule(params.get("ruleId").trim(),ftpAuditService.getUserFromPrincipal(user));
		} catch(Exception e) {
			LOG.error("Error in delete rule"+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End deleteFtpRule");
		return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
	}
}
