package com.evolvus.abk.ftp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FtpProductBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.FtpRuleConfiguration;
import com.evolvus.abk.ftp.domain.ItemMaster;
import com.evolvus.abk.ftp.domain.ParameterConfiguration;
import com.evolvus.abk.ftp.domain.ProductVariableFields;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.FtpProductRepository;
import com.evolvus.abk.ftp.repository.FtpRuleConfigRepository;
import com.evolvus.abk.ftp.repository.ItemRepository;
import com.evolvus.abk.ftp.repository.ParamRepository;

@Service
public class FtpRuleConfigService {

	@Autowired
	FtpRuleConfigRepository ftpRuleConfigRepository;

	@Autowired
	FtpProductRepository ftpProductRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ParamRepository paramRepository;

	@Autowired
	FtpAuditService ftpAuditService;

	private static final Logger LOG = LoggerFactory.getLogger(FtpRuleConfigService.class);

	public Pageable createPageRequest(Integer currentPage, Integer pageSize) {
		return (new PageRequest(currentPage, pageSize, new Sort(Direction.DESC, "createdDate")));
	}

	public CustomResponse saveRules(FtpProductBean product, User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start saveRules");
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setBankCode(user.getEntity());
		audit.setTxnUser(user.getUsername());

		FtpRuleConfiguration rule = null;
		audit.setTxnObjectType(FtpRuleConfiguration.class.getName());
		try {
			if (product.getRule() != null && product.getRule().getRuleId() != null) {
				rule = ftpRuleConfigRepository.findOne(product.getRule().getRuleId());
				audit.setTxnAction(Constants.TXN_UPDATE);
				if (rule == null) {
					response.setStatus(Constants.STATUS_FAIL);
					response.setDescription("Rule does not exists.");
				} else {
					response.setStatus(Constants.STATUS_OK);
					response.setDescription("Rule Updated Successfully.");
					paramRepository.deleteByRuleConfig(rule);
					audit.setPreTxnVal(ftpAuditService.objectToJson(rule));
					rule = processRule(rule, user, product);
					rule.setLastUpdatedBy(user.getUsername());
					rule.setLastUpdatedDate(ftpAuditService.getCurrentTime());
					ftpRuleConfigRepository.save(rule);
					audit.setPostTxnVal(ftpAuditService.objectToJson(rule));
				}
			} else {
				rule = new FtpRuleConfiguration();
				audit.setTxnAction(Constants.TXN_CREATE);
				Long duplicate = ftpRuleConfigRepository.countByRuleName(product.getRule().getRuleName());
				if (duplicate > 0) {
					response.setStatus(Constants.STATUS_FAIL);
					response.setDescription("Rule Name already exists.");
				} else {
					response.setStatus(Constants.STATUS_OK);
					response.setDescription("Rule Created Successfully.");
					audit.setPreTxnVal(ftpAuditService.objectToJson(rule));
					rule = processRule(rule, user, product);
					rule.setCreatedBy(user.getUsername());
					rule.setCreatedDate(ftpAuditService.getCurrentTime());
					ftpRuleConfigRepository.save(rule);
					audit.setPostTxnVal(ftpAuditService.objectToJson(rule));
				}
			}
		} catch (Exception e) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error("Error in Saving Rule =>" + audit.getStackTrace());
		}
		LOG.debug("End saveRules");
		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		ftpAuditService.logAudit(audit);
		return response;
	}

	public FtpRuleConfiguration processRule(FtpRuleConfiguration rule, User user, FtpProductBean product) {
		LOG.debug("Start processRule");
		try {
			FtpProduct ftpProduct = ftpProductRepository.findByProductCodeAndBankCode(product.getCode(),
					user.getEntity());
			rule.setRuleName(product.getRule().getRuleName());
			rule.setTransferMethod(product.getRule().getTransferMethod());

			rule.setIsYieldCurveApplicable(product.getRule().getIsYieldCurveApplicable());
			if (product.getRule().getIsYieldCurveApplicable()) {
				rule.setYieldTenor(product.getRule().getYieldTenor());
			} else {
				rule.setYieldTenor(Constants.EMPTY);
			}

			rule.setIsKeyCurveApplicable(product.getRule().getIsKeyCurveApplicable());
			if (product.getRule().getIsKeyCurveApplicable()) {
				rule.setKeyRateCode(product.getRule().getKeyRateCode());
			} else {
				rule.setKeyRateCode(Constants.EMPTY);
			}

			rule.setIsMrgnAdjstTempApplicable(product.getRule().getIsMrgnAdjstTempApplicable());
			if (product.getRule().getIsMrgnAdjstTempApplicable()) {
				rule.setMrgnAdjstTempCode(product.getRule().getMrgnAdjstTempCode());
			} else {
				rule.setMrgnAdjstTempCode(Constants.EMPTY);
			}

			rule.setIsMrgnAdjstExtApplicable(product.getRule().getIsMrgnAdjstExtApplicable());
			if (product.getRule().getIsMrgnAdjstExtApplicable()) {
				rule.setMrgnAdjstExtCode(product.getRule().getMrgnAdjstExtCode());
			} else {
				rule.setMrgnAdjstExtCode(Constants.EMPTY);
			}

			rule.setCreatedDate(new Date());
			rule.setCreatedBy(user.getUsername());
			rule.setLastUpdatedBy(null);
			rule.setLastUpdatedDate(null);
			ParameterConfiguration param = null;
			rule.setProduct(ftpProduct);
			rule.setBankCode(user.getEntity());

			List<ParameterConfiguration> parameters = new ArrayList<ParameterConfiguration>();
			for (ProductVariableFields variableField : product.getVariableFields()) {
				if (Constants.FIELD_TYPE_CHIPS.equals(variableField.getFieldType())
						|| Constants.FIELD_TYPE_PRECHIPS.equals(variableField.getFieldType())
						|| Constants.FIELD_TYPE_MULTISELECT.equals(variableField.getFieldType())) {

					for (ItemMaster item : variableField.getListValue()) {
						param = new ParameterConfiguration();
						param.setKeyName(variableField.getKeyName());
						param.setParamVal(item.getItemCode());
						param.setOperatorValue(variableField.getOperator());
						param.setProductCode(product.getCode());
						param.setRuleConfig(rule);
						param.setBankCode(user.getEntity());
						parameters.add(param);
					}
				} else {
					if (variableField.getTextValue() != null && !Constants.EMPTY.equals(variableField.getTextValue())) {
						param = new ParameterConfiguration();
						param.setKeyName(variableField.getKeyName());

						if (Constants.FIELD_TYPE_DATE.equals(variableField.getFieldType())
								|| Constants.FIELD_TYPE_DATE_C.equals(variableField.getFieldType())) {
							param.setParamVal(variableField.getDateValue());
						} else {
							param.setParamVal(variableField.getTextValue());
						}
						param.setOperatorValue(variableField.getOperator());
						param.setProductCode(product.getCode());
						param.setRuleConfig(rule);
						param.setBankCode(user.getEntity());
						parameters.add(param);
					} 
				}
			}
			rule.setParams(parameters);
		}  catch (NullPointerException e) {
			LOG.error("Error in processing rule=>" + ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception e) {
			LOG.error("Error in processing rule=>" + ExceptionUtils.getStackTrace(e));
			throw e;
		}
		LOG.debug("End processRule");
		return rule;
	}
	
	public FtpProductBean findRuleById(Long ruleId, User user) {
		FtpProductBean product = new FtpProductBean();
		LOG.debug("Start : findRuleById");
		try {
			FtpRuleConfiguration rule = ftpRuleConfigRepository.findOne(ruleId);
			for (ProductVariableFields field : rule.getProduct().getVariableFields()) {
				
				if (Constants.FIELD_TYPE_SELECT.equalsIgnoreCase(field.getFieldType())
						|| Constants.FIELD_TYPE_MULTISELECT.equals(field.getFieldType()) && field.getFieldId() != null
						|| Constants.FIELD_TYPE_PRECHIPS.equals(field.getFieldType())) {
					
					field.setItemList(itemRepository.findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc(
							field.getFieldId(), user.getEntity()));
				
				}
			}
			for (ParameterConfiguration param : rule.getParams()) {
				for (ProductVariableFields field : rule.getProduct().getVariableFields()) {
					if (field.getKeyName().equals(param.getKeyName())) {
						field.setOperator(param.getOperatorValue());
						if (Constants.FIELD_TYPE_MULTISELECT.equals(field.getFieldType())
								|| Constants.FIELD_TYPE_PRECHIPS.equals(field.getFieldType())) {
							for (ItemMaster item : field.getItemList()) {
								if (param.getParamVal().equalsIgnoreCase(item.getItemCode())) {
									field.getListValue().add(item);
								}
							}
						} else if (Constants.FIELD_TYPE_CHIPS.equals(field.getFieldType())) {
							ItemMaster chipField = new ItemMaster();
							chipField.setItemCode(param.getParamVal());
							chipField.setItemValue(param.getParamVal());
							field.getListValue().add(chipField);
						} else {
							field.setTextValue(param.getParamVal());
						}
					}
				}
			}
			product.setVariableFields(rule.getProduct().getVariableFields());
			product.setRule(rule);
			product.setCode(rule.getProduct().getProductCode());
			product.setName(rule.getProduct().getProductName());
		} catch (Exception e) {
			LOG.error("Error in fetching rule:=>"+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : findRuleById");
		return product;
	}

	@Transactional
	public CustomResponse deleteRule(String ruleId,User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start deleteRule");
		FtpAudit audit = new FtpAudit();
		audit.setBankCode(user.getEntity());
		audit.setTxnAction(Constants.TXN_DELETE);
		audit.setTxnObjectType(FtpRuleConfiguration.class.getName());
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnUser(user.getUsername());
		try {
			FtpRuleConfiguration ruleToDelete = ftpRuleConfigRepository.findOne(Long.valueOf(ruleId));
			if (ruleToDelete != null) {
				audit.setPreTxnVal(ftpAuditService.objectToJson(ruleToDelete));
				ftpRuleConfigRepository.delete(ruleToDelete);
				response.setStatus(Constants.STATUS_OK);
				response.setDescription("Rule deleted sucessfully.");
			} else {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Failed to delete rule.");
			}
		} catch (NumberFormatException ne) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(ne));
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Failed to delete rule. Invalid rule id.");
			LOG.error("Error in delete rule =>"+audit.getStackTrace());
		} catch (Exception e) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Failed to delete rule.");
			LOG.error("Error in delete rule =>"+audit.getStackTrace());
		}
		audit.setMessage(response.getDescription());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setTxnStatus(response.getStatus());
		ftpAuditService.logAudit(audit);
		LOG.debug("End deleteRule");
		return response;
	}

	public Page<FtpRuleConfiguration> getPagedRules(FtpProduct product, String searchString, Pageable pageData,User user) {
		Page<FtpRuleConfiguration> pagedRules = null;
		LOG.debug("Start getPagedRules");
		try {
			pagedRules = ftpRuleConfigRepository.findByBankCodeAndProductAndRuleNameContaining(user.getEntity(),product, searchString, pageData);
		} catch (Exception e) {
			LOG.error("Error in paged rules =>"+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getPagedRules");
		return pagedRules;
	}
}
