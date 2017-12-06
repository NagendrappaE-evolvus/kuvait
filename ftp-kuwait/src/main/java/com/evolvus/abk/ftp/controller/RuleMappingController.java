package com.evolvus.abk.ftp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.AssignMappingRuleBean;
import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.RuleMappingService;

@RestController
@RequestMapping("rule-map")
public class RuleMappingController {

	private static final Logger LOG = LoggerFactory.getLogger(RuleMappingController.class);

	@Autowired
	RuleMappingService ruleMappingService;

	@Autowired
	FtpAuditService ftpAuditService;

	@RequestMapping(value = "/getTargets", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getAllTargets(@RequestBody Map<String, String> data, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getAllTragets");
		CustomResponse customResponse = new CustomResponse();
		try {
			List<String> targets = ruleMappingService.getTargetsByTypeAndProduct(data.get("targetType"),
					data.get("productCode"), ftpAuditService.getUserFromPrincipal(user));
			if(targets!=null && !targets.isEmpty() && targets.size()>0) {
				customResponse.setData(targets);
				customResponse.setStatus(Constants.STATUS_OK);
			} else {
				customResponse.setData(targets);
				customResponse.setDescription("Data not available.");
				customResponse.setStatus(Constants.STATUS_FAIL);
			}
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in fetching targets.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getAllTragets");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/getRuleMappings", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getRuleMappings(@RequestBody Map<String, String> data, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getRuleMappings");
		CustomResponse customResponse = new CustomResponse();
		try {
			customResponse.setData(ruleMappingService.getRuleMappings(data.get("targetType"), data.get("productCode"),
					ftpAuditService.getUserFromPrincipal(user)));
			customResponse.setStatus(Constants.STATUS_OK);
			customResponse.setDescription("Fetched rule mappings.");
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in fetching rule mappings.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/save-assign-rules", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> saveAssignRules(@RequestBody AssignMappingRuleBean ruleMapBean,
			Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start saveAssignRules");
		CustomResponse customResponse = null;
		try {
			customResponse = ruleMappingService.saveRuleMappings(ruleMapBean,
					ftpAuditService.getUserFromPrincipal(user));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in saving rule mappings.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End saveAssignRules");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/get-assign-rule", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getAssignRule(@RequestBody Map<String, String> requestInput, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getAssignRule");
		AssignMappingRuleBean ruleMap = null;
		CustomResponse customResponse = new CustomResponse();
		try {
			ruleMap = ruleMappingService.getRuleMap(requestInput.get("target"),
					requestInput.get("targetType"), requestInput.get("productCode"),
					ftpAuditService.getUserFromPrincipal(user));
			if (null != ruleMap) {
				customResponse.setData(ruleMap);
				customResponse.setStatus(Constants.STATUS_OK);
			} else {
				customResponse.setStatus(Constants.STATUS_FAIL);
				customResponse.setDescription("Rule mappings not available.");
			}
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in getting assign rules.");
			LOG.error(customResponse.getDescription() + ExceptionUtils.getStackTrace(e));
		}

		LOG.debug("End getAssignRule");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/remove-assign-rules", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> deleteAssignRules(@RequestBody AssignMappingRuleBean ruleMapBean,
			Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("End deleteAssignRules");
		CustomResponse customResponse = null;
		try {
			customResponse = ruleMappingService.deleteRuleMappings(ruleMapBean,ftpAuditService.getUserFromPrincipal(user));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in delete rule mappings.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End deleteAssignRules");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}
}
