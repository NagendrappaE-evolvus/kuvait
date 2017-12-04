package com.evolvus.abk.ftp.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.ProductSchemeCodeMapBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.ProductVariableFields;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.FtpProductRepository;
import com.evolvus.abk.ftp.repository.ItemRepository;

@Service
public class ProductService {

	@Autowired
	FtpProductRepository ftpProductRepository;

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	FtpAuditService ftpAuditService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

	public List<Map<String, String>> getAllProductsForMenu(User user) {
		List<Map<String, String>> productMenu = Collections.emptyList();
		LOG.debug("Start getAllProductsForMenu");
		try {
			productMenu = ftpProductRepository.findAllProductCodeAndProductNameByBankCode(user.getEntity());
		} catch(Exception e) {
			LOG.error("Error in fetching productsForMenu."+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getAllProductsForMenu");
		return productMenu;
	}

	public FtpProduct getProductByCode(String productCode, User user) {
		LOG.debug("Start getProductByCode with variable fields");
		FtpProduct product = null;
		try {
			product = ftpProductRepository.findByProductCodeAndBankCode(productCode,user.getEntity());
			if (product != null) {
				for (ProductVariableFields field : product.getVariableFields()) {
					if ("PRECHIPS".equalsIgnoreCase(field.getFieldType())
							|| "SELECT".equalsIgnoreCase(field.getFieldType())
							|| "MULTISELECT".equals(field.getFieldType()) && field.getFieldId() != null) {
						field.setItemList(
								itemRepository.findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc(field.getFieldId(),user.getEntity()));
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error in fetching Product By Code with variable fields."+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getProductByCode with variable fields");
		return product;
	}

	public FtpProduct findProductByCode(String productCode,User user) {
		FtpProduct product = null; 
		LOG.debug("Start findProductByCode without variable fields");
		try {
			product = ftpProductRepository.findByProductCodeAndBankCode(productCode,user.getEntity());
		} catch(Exception e) {
			LOG.error("error in fetching Product By Code without variable fields");
		}
		LOG.debug("End findProductByCode without variable fields");
		return product;
	}

	public CustomResponse saveProductSchemesMap(ProductSchemeCodeMapBean prodSchemes,User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start saveProductSchemesMap");
		FtpAudit audit = new FtpAudit();
		audit.setTxnAction(Constants.TXN_SAVE);
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnUser(user.getUsername());
		try {
			audit.setBankCode(user.getEntity());
			FtpProduct product = ftpProductRepository.findByProductCodeAndBankCode(prodSchemes.getProductCode(),user.getEntity());
			audit.setPreTxnVal(ftpAuditService.objectToJson(product.getSchemes()));
			product.setSchemes(prodSchemes.getProdSchemes());
			
			prodSchemes.getProdSchemes().stream().forEach(scheme->scheme.setBankCode(user.getEntity()));
			
			audit.setTxnObjectType(product.getSchemes().getClass().getName());
			
			ftpProductRepository.save(product);
			
			audit.setPostTxnVal(ftpAuditService.objectToJson(product.getSchemes()));
			
			product.setLastUpdatedBy(user.getUsername());
			product.setLastUpdatedDate(ftpAuditService.getCurrentTime());
			
			response.setStatus(Constants.STATUS_OK);
			response.setDescription("Scheme codes mapped successfully.");
		} catch (Exception e) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error in mapping Scheme codes.");
			LOG.error("Error in save Product Schemes Map. =>"+audit.getStackTrace());
		}
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		ftpAuditService.logAudit(audit);
		LOG.debug("End saveProductSchemesMap");
		return response;
	}
}
