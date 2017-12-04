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
import com.evolvus.abk.ftp.bean.SchemeCodeBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.KwtFtpSchemes;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.ProductService;
import com.evolvus.abk.ftp.service.impl.SchemesService;

@RestController
@RequestMapping("schemes")
public class SchemesController {

	private static final Logger LOG = LoggerFactory.getLogger(SchemesController.class);

	@Autowired
	private SchemesService schemeService;

	@Autowired
	private ProductService productService;

	@Autowired
	FtpAuditService ftpAuditService;

	@RequestMapping(value = "/getAll", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getAllSchemes(Principal user) throws InterruptedException {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getAllSchemes");
		CustomResponse customResponse = new CustomResponse();
		try {
			Iterable<KwtFtpSchemes> schemes = schemeService.getAll(ftpAuditService.getUserFromPrincipal(user));
			if (schemes != null) {
				customResponse.setDescription("Fetched All Ftp Schemes");
				customResponse.setData(schemes);
			} else {
				customResponse.setDescription("No scheme codes available.");
			}
			customResponse.setStatus(Constants.STATUS_OK);
		} catch (Exception e) {
			customResponse.setDescription("Error in fetching Ftp Schemes");
			customResponse.setStatus(Constants.STATUS_OK);
			LOG.error("Error in fetching all schemes.");
		}

		LOG.debug("End getAllSchemes");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> saveSchemeCode(@RequestBody SchemeCodeBean scheme, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start saveSchemeCode");
		CustomResponse customResponse = null;
		try {
			customResponse = schemeService.saveScheme(scheme, ftpAuditService.getUserFromPrincipal(user));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in saving scheme code.");
			LOG.error(customResponse.getDescription() + "=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End saveSchemeCode");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> updateSchemeCode(@RequestBody SchemeCodeBean scheme, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start updateSchemeCode");
		CustomResponse customResponse = null;
		try {
			customResponse = schemeService.updateScheme(scheme, ftpAuditService.getUserFromPrincipal(user));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in update scheme code.");
			LOG.error(customResponse.getDescription() + " => " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End updateSchemeCode");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> deleteSchemeCode(@RequestBody SchemeCodeBean scheme, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start deleteSchemeCode");
		CustomResponse customResponse = null;
		try {
			customResponse = schemeService.deleteScheme(scheme, ftpAuditService.getUserFromPrincipal(user));
		} catch (Exception e) {
			customResponse = new CustomResponse();
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in deleting scheme code.");
			LOG.error(customResponse.getDescription() + " => " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End deleteSchemeCode");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/schemes-paged", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getSchemesPaged(@RequestBody Map<String, String> params, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getSchemesPaged");
		CustomResponse customResponse = new CustomResponse();
		Integer currentPage = 0;
		Pageable pageData = null;
		Integer pageSize = 0;
		String searchString = "";
		try {
			if (params != null) {
				currentPage = Integer.parseInt(params.get("currentPage"));
				pageSize = Integer.parseInt(params.get("pageSize"));
				searchString = params.get("searchString") != null ? params.get("searchString") : "";
				pageData = schemeService.createPageRequest(currentPage, pageSize);
				customResponse.setData(
						schemeService.getPagedSchemes(searchString, pageData, ftpAuditService.getUserFromPrincipal(user)));
				customResponse.setStatus(Constants.STATUS_OK);
			} else {
				throw new IllegalArgumentException();
			}
			
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in paging scheme codes.");
			LOG.error(customResponse.getDescription() + " => " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getSchemesPaged");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/getSchmMap/{code}", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getByCode(@PathVariable String code, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getByCode => /getSchmMap/"+code);
		CustomResponse customResponse = new CustomResponse();
		FtpProduct product = null;
		try {
			product = productService.findProductByCode(code, ftpAuditService.getUserFromPrincipal(user));
			customResponse.setData(product.getSchemes());
			if(product.getSchemes()==null || product.getSchemes().isEmpty() || product.getSchemes().size() == Constants.ZERO) {
				customResponse.setDescription("No scheme codes are mapped to "+product.getProductName()+".");
				customResponse.setStatus(Constants.STATUS_FAIL);
			} else {
				customResponse.setStatus(Constants.STATUS_OK);
			}
			
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			customResponse.setDescription("Error in fetching scheme codes.");
			LOG.error(customResponse.getDescription() + " => " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getByCode => /getSchmMap/"+code);
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}
}