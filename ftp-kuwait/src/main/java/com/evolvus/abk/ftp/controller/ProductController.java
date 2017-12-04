package com.evolvus.abk.ftp.controller;

import java.security.Principal;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.ProductSchemeCodeMapBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	FtpAuditService ftpAuditService;

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping(value = "/get-menu-items", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getAllProducts(Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getAllProducts");
		CustomResponse customResponse = new CustomResponse();
		try {
			customResponse.setData(productService.getAllProductsForMenu(ftpAuditService.getUserFromPrincipal(user)));
			customResponse.setDescription("Fetched.");
			customResponse.setStatus(Constants.STATUS_OK);
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			LOG.error("Error in fetching products."+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getAllProducts");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/getByCode/{code}", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> getByCode(@PathVariable String code, Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		LOG.debug("Start getByCode");
		CustomResponse customResponse = new CustomResponse();
		customResponse.setDescription("Fetched.");
		try {
			FtpProduct product = productService.getProductByCode(code,ftpAuditService.getUserFromPrincipal(user));
			customResponse.setData(product);
			customResponse.setStatus(Constants.STATUS_OK);
		} catch (Exception e) {
			customResponse.setStatus(Constants.STATUS_FAIL);
			LOG.error("Error in get product by code");
		}
		LOG.debug("End getByCode");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

	@RequestMapping(value = "/save-code-map", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> saveSchemeCodeMap(@RequestBody ProductSchemeCodeMapBean mapBean,
			Principal user) {
		HttpStatus httpStatus = HttpStatus.OK;
		CustomResponse customResponse = null;
		LOG.debug("Start saveSchemeCodeMap");
		try {
			customResponse = productService.saveProductSchemesMap(mapBean,ftpAuditService.getUserFromPrincipal(user));
		} catch(Exception e) {
			customResponse = new CustomResponse();
			customResponse.setDescription("Error in saving scheme code map.");
			customResponse.setStatus(Constants.STATUS_FAIL);
			LOG.error(customResponse.getDescription()+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End saveSchemeCodeMap");
		return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
	}

}
