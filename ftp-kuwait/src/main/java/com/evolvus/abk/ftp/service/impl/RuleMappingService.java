package com.evolvus.abk.ftp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.AssignMappingRuleBean;
import com.evolvus.abk.ftp.bean.AssignRuleBean;
import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.ExpressionBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.RuleExpressions;
import com.evolvus.abk.ftp.domain.RuleMappings;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.FtpProductRepository;
import com.evolvus.abk.ftp.repository.RuleExpressionsRepository;
import com.evolvus.abk.ftp.repository.RuleMappingRepository;

@Service
public class RuleMappingService {

	private static final Logger LOG = LoggerFactory.getLogger(RuleMappingService.class);

	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	RuleMappingRepository ruleMappingRepository;

	@Autowired
	FtpProductRepository ftpProductRepository;

	@Autowired
	RuleExpressionsRepository ruleExpressionsRepository;

	public List<String> getTargetsByTypeAndProduct(String targetType, String productCode, User user) {
		List<String> targetList = Collections.emptyList();
		LOG.debug("Start getTargetsByTypeAndProduct");
		try {
			targetList = ruleMappingRepository.findDistinctRuleTargetByTargetTypeAndProduct(targetType,
					ftpProductRepository.findByProductCodeAndBankCode(productCode, user.getEntity()));
		} catch (Exception e) {
			LOG.error("Error in fetching targets." + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getTargetsByTypeAndProduct");
		return targetList;
	}

	public List<RuleMappings> getRuleMappings(String targetType, String productCode, User user) {
		LOG.debug("Start getRuleMappings");
		List<RuleMappings> ruleMappingList = Collections.emptyList();
		try {
			ruleMappingList = ruleMappingRepository.findByTargetTypeAndProduct(targetType,
					ftpProductRepository.findByProductCodeAndBankCode(productCode, user.getEntity()));
		} catch (Exception e) {
			LOG.error("Error in fetching rule mappings=>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getRuleMappings");
		return ruleMappingList;
	}

	public AssignMappingRuleBean getRuleMap(String target, String targetType, String productCode, User user) {
		LOG.debug("Start getRuleMap");
		AssignMappingRuleBean ruleBean = new AssignMappingRuleBean();
		try {
			FtpProduct product = ftpProductRepository.findByProductCodeAndBankCode(productCode, user.getEntity());
			List<RuleMappings> ruleMappings = ruleMappingRepository.findByTargetTypeAndRuleTargetAndProduct(targetType,
					target, product);
			ruleBean.setModalTarget(target);
			ruleBean.setProductCode(product.getProductCode());
			List<AssignRuleBean> assignRuleBeansList = new ArrayList<AssignRuleBean>();

			for (RuleMappings ruleMap : ruleMappings) {
				AssignRuleBean assignRuleBean = new AssignRuleBean();
				assignRuleBean.setRuleId(ruleMap.getRuleId().toString());
				assignRuleBean.setRuleName(ruleMap.getRuleName());
				assignRuleBean.setRuleTarget(ruleMap.getRuleTarget());
				assignRuleBean.setTargetType(ruleMap.getTargetType());
				assignRuleBean.setCrudOperation(Constants.CRUD_UPDATE);
				List<ExpressionBean> expressionBeans = new ArrayList<ExpressionBean>();

				for (RuleExpressions ruleExp : ruleMap.getExpressions()) {
					Boolean expAvl = Boolean.FALSE;
					for (ExpressionBean avlBean : expressionBeans) {
						if (ruleExp.getExpName().equalsIgnoreCase(avlBean.getExpName())) {
							expAvl = Boolean.TRUE;
							avlBean.getExpValues().add(ruleExp.getExpValue());
						}
					}
					if (!expAvl) {
						ExpressionBean newExpBean = new ExpressionBean();
						newExpBean.setExpName(ruleExp.getExpName());
						newExpBean.setExpOpr(ruleExp.getExpOpr());
						newExpBean.setExpTargetType(ruleExp.getExpTargetType());
						newExpBean.setCrudOperation(Constants.CRUD_UPDATE);
						List<String> expValues = new ArrayList<String>();
						expValues.add(ruleExp.getExpValue());
						newExpBean.setExpValues(expValues);
						expressionBeans.add(newExpBean);
					}
				}
				assignRuleBean.setExpressions(expressionBeans);
				assignRuleBeansList.add(assignRuleBean);
			}
			ruleBean.setRuleMappings(assignRuleBeansList);
		} catch (Exception e) {
			LOG.error("Error in fetching rules" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getRuleMap");
		return ruleBean;
	}

	public CustomResponse saveRuleMappings(AssignMappingRuleBean ruleMapBean, User user) {
		CustomResponse response = new CustomResponse();
		FtpAudit consolidatedAudit = new FtpAudit();
		consolidatedAudit.setTxnStartTime(ftpAuditService.getCurrentTime());
		consolidatedAudit.setTxnUser(user.getUsername());
		consolidatedAudit.setBankCode(user.getEntity());
		List<FtpAudit> auditList = new ArrayList<FtpAudit>(4);
		try {
			if (ruleMapBean == null || ruleMapBean.getModalTarget() == null || ruleMapBean.getModalTarget().isEmpty()
					|| ruleMapBean.getProductCode() == null || ruleMapBean.getProductCode().isEmpty()) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Invalid Data Received.");
			} else {
				FtpProduct ftpProduct = ftpProductRepository.findByProductCodeAndBankCode(ruleMapBean.getProductCode(),
						user.getEntity());
				List<RuleMappings> ruleMappingList = new ArrayList<RuleMappings>(4);
				RuleMappings ruleMap = null;
				for (AssignRuleBean rule : ruleMapBean.getRuleMappings()) {
					FtpAudit audit = new FtpAudit();
					audit.setTxnStartTime(ftpAuditService.getCurrentTime());
					audit.setTxnUser(user.getUsername());
					audit.setBankCode(user.getEntity());
					if (rule.getRuleId() != null && !rule.getRuleId().isEmpty()) {
						ruleMap = ruleMappingRepository.findOne(Long.parseLong(rule.getRuleId()));
						if (ruleMap == null) {
							response.setDescription("Error in updating rules.");
							response.setStatus(Constants.STATUS_FAIL);
						} else {
							audit.setPreTxnVal(ftpAuditService.objectToJson(ruleMap));
							if (rule.getCrudOperation() == Constants.CRUD_DELETE) {
								this.deleteRuleMap(ruleMap);
								response.setDescription("Rule Deleted Successfully.");
								response.setStatus(Constants.STATUS_OK);
							} else {
								this.deleteRuleMap(ruleMap);
								ruleMap.setRuleId(null);
								ruleMap = processRuleMap(ruleMapBean, ruleMap, user, rule, ftpProduct);
								ruleMap.setLastUpdatedDate(ftpAuditService.getCurrentTime());
								ruleMap.setUpdatedBy(user.getUsername());
								audit.setTxnAction(Constants.TXN_UPDATE);
								response.setDescription("Rule Updated Successfully.");
								response.setStatus(Constants.STATUS_OK);
							}
						}
					} else {
						ruleMap = new RuleMappings();
						
						ruleMap = processRuleMap(ruleMapBean, ruleMap, user, rule, ftpProduct);

						ruleMap.setCreatedBy(user.getUsername());
						ruleMap.setCreatedDate(ftpAuditService.getCurrentTime());
						response.setDescription("Rule Saved Successfully.");
						audit.setTxnAction(Constants.TXN_CREATE);
						response.setStatus(Constants.STATUS_OK);
					}
					/* processRuleMap */
					if (rule.getCrudOperation() != Constants.CRUD_DELETE) {
						ruleMappingList.add(ruleMap);
					} else {
						audit.setTxnAction(Constants.TXN_DELETE);
					}
					audit.setTxnObjectType(RuleMappings.class.getName());
					audit.setTxnEndTime(ftpAuditService.getCurrentTime());
					audit.setPostTxnVal(ftpAuditService.objectToJson(ruleMap));
					audit.setMessage(response.getDescription());
					audit.setTxnStatus(response.getStatus());
					auditList.add(audit);
				}
				ruleMappingRepository.save(ruleMappingList);
				consolidatedAudit.setTxnObjectType(ruleMappingList.getClass().getName());
			}
		} catch (Exception e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Invalid Data Received.");
			consolidatedAudit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + "=>" + consolidatedAudit.getStackTrace());
		}
		consolidatedAudit.setBankCode(user.getEntity());
		consolidatedAudit.setMessage(response.getDescription());
		consolidatedAudit.setTxnStatus(response.getStatus());
		consolidatedAudit.setTxnEndTime(ftpAuditService.getCurrentTime());
		auditList.add(consolidatedAudit);
		ftpAuditService.logAudit(auditList);
		LOG.debug("End saveRuleMappings");
		return response;
	}

	public RuleMappings processRuleMap(AssignMappingRuleBean ruleMapBean, RuleMappings ruleMap, User user,
			AssignRuleBean rule, FtpProduct ftpProduct) {
		LOG.debug("Start processRuleMap");
		ruleMap.setProduct(ftpProduct);
		ruleMap.setRuleName(rule.getRuleName());
		ruleMap.setRuleTarget(ruleMapBean.getModalTarget());
		ruleMap.setTargetType(rule.getTargetType());
		ruleMap.setBankCode(user.getEntity());
		List<RuleExpressions> expressions = new ArrayList<RuleExpressions>(2);
		for (ExpressionBean exp : rule.getExpressions()) {
			if(exp.getExpValues()!=null && exp.getExpValues().size()>0)
			for (String val : exp.getExpValues()) {
				RuleExpressions ruleExp = new RuleExpressions();
				ruleExp.setExpName(exp.getExpName());
				ruleExp.setExpTargetType(rule.getTargetType());
				ruleExp.setExpValue(val);
				ruleExp.setBankCode(user.getEntity());
				if (exp.getExpOpr() != null && !exp.getExpOpr().isEmpty()) {
					ruleExp.setExpOpr(exp.getExpOpr());
				} else {
					ruleExp.setExpOpr(Constants.OP_EQUAL);
				}
				ruleExp.setRuleMap(ruleMap);
				ruleExp.setProduct(ftpProduct);
				if (!Constants.EMPTY.equals(exp.getExpName()) || exp.getCrudOperation() != Constants.CRUD_DELETE) {
					expressions.add(ruleExp);
				}
			}
		}
		ruleMap.setExpressions(expressions);
		ruleMap.setProduct(ftpProduct);
		LOG.debug("End processRuleMap");
		return ruleMap;
	}
	
	@Transactional
	public void deleteRuleMap(RuleMappings ruleMap) {
		try {
			ruleExpressionsRepository.deleteByRuleMap(ruleMap);
			ruleMappingRepository.delete(ruleMap);
		} catch (Exception e) {
			LOG.error("Error in deleting rule map " + ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}
	
	@Transactional
	public void deleteExpressions(RuleMappings ruleMap) {
		try {
			for(RuleExpressions exp : ruleMap.getExpressions()) {
				ruleExpressionsRepository.delete(exp);
			}
		} catch (Exception e) {
			LOG.error("Error in deleting rule map " + ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}

	public CustomResponse deleteRuleMappings(AssignMappingRuleBean ruleMapBean, User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start deleteRuleMappings");
		FtpAudit audit = new FtpAudit();
		audit.setTxnAction(Constants.TXN_DELETE);
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setBankCode(user.getEntity());
		audit.setTxnObjectType(AssignMappingRuleBean.class.getName());
		try {
			if (ruleMapBean == null || ruleMapBean.getModalTarget() == null || ruleMapBean.getModalTarget().isEmpty()
					|| ruleMapBean.getProductCode() == null || ruleMapBean.getProductCode().isEmpty()) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Invalid Data Received.");
			} else {
				RuleMappings ruleMap = null;
				audit.setPreTxnVal(ftpAuditService.objectToJson(ruleMapBean));
				for (AssignRuleBean rule : ruleMapBean.getRuleMappings()) {
					if (rule.getRuleId() != null && !rule.getRuleId().isEmpty()) {
						ruleMap = ruleMappingRepository.findOne(Long.parseLong(rule.getRuleId()));
						this.deleteRuleMap(ruleMap);
					}
				}
				response.setDescription("Rule Deleted Successfully.");
				response.setStatus(Constants.STATUS_OK);
			}
		} catch (Exception e) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error in delete Rule Mappings.");
			LOG.error(response.getDescription() + "=>" + audit.getStackTrace());
		}
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setTxnStatus(response.getStatus());
		audit.setMessage(response.getDescription());
		LOG.debug("End deleteRuleMappings");
		return response;
	}
}
