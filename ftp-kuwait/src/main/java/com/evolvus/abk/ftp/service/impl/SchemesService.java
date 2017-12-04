package com.evolvus.abk.ftp.service.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.SchemeCodeBean;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.KwtFtpSchemes;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.SchemeCodesRepository;;

@Service
public class SchemesService {

	@Autowired
	SchemeCodesRepository schemeCodesRepository;

	@Autowired
	FtpAuditService ftpAuditService;

	private static final Logger LOG = LoggerFactory.getLogger(SchemesService.class);

	public Iterable<KwtFtpSchemes> getAll(User user) {
		Iterable<KwtFtpSchemes> allSchemes = null;
		LOG.debug("Start getAll scheme codes");
		try {
			allSchemes = schemeCodesRepository.findAllByBankCode(user.getEntity());
		} catch (Exception e) {
			LOG.error("Error in fetching scheme codes" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getAll scheme codes");
		return allSchemes;
	}

	public CustomResponse saveScheme(SchemeCodeBean scheme, User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start Save scheme Code");
		if (scheme.getFtpCategory() == null || Constants.EMPTY.equals(scheme.getFtpCategory())) {
			scheme.setFtpCategory(" ");
		}
		FtpAudit audit = new FtpAudit();

		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_CREATE);
		audit.setTxnUser(user.getUsername());
		audit.setBankCode(user.getEntity());
		audit.setTxnObjectType(KwtFtpSchemes.class.getName());

		try {
			KwtFtpSchemes schemeCode = schemeCodesRepository.findBySchmCodeAndGlSubHeadCodeAndFtpCategory(
					scheme.getSchemeCode(), scheme.getGlSubHeadCode(), scheme.getFtpCategory());
			if (schemeCode != null) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Scheme Code already exists.");
			} else {
				schemeCode = new KwtFtpSchemes();
				schemeCode.setSchmCode(scheme.getSchemeCode());
				schemeCode.setSchmDesc(scheme.getSchemeDesc());
				schemeCode.setGlSubHeadCode(scheme.getGlSubHeadCode());
				schemeCode.setFtpCategory(scheme.getFtpCategory());
				schemeCode.setCreatedBy(user.getName());
				schemeCode.setBankCode(user.getEntity());
				schemeCode.setCreatedDate(ftpAuditService.getCurrentTime());
				schemeCode = schemeCodesRepository.save(schemeCode);
				audit.setPostTxnVal(ftpAuditService.objectToJson(schemeCode));
				response.setStatus(Constants.STATUS_OK);
				response.setDescription("Scheme Code Saved.");
			}
		} catch (Exception e) {
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error while saving Scheme Code.");
			LOG.error(response.getDescription() + "=>" + audit.getStackTrace());
		}
		audit.setTxnStatus(response.getStatus());
		audit.setMessage(response.getDescription());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		ftpAuditService.logAudit(audit);
		LOG.debug("End Save scheme Code");
		return response;
	}

	public CustomResponse deleteScheme(SchemeCodeBean scheme, User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start deleteScheme");
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_DELETE);
		audit.setTxnUser(user.getUsername());
		audit.setBankCode(user.getEntity());
		audit.setTxnObjectType(KwtFtpSchemes.class.getName());

		try {
			KwtFtpSchemes schemeCode = schemeCodesRepository.findOne(scheme.getSchemeId());
			if (schemeCode == null) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Scheme Code does not exists.");
			} else {
				audit.setPreTxnVal(ftpAuditService.objectToJson(schemeCode));
				schemeCodesRepository.delete(schemeCode);
				response.setStatus(Constants.STATUS_OK);
				response.setDescription("Scheme Code Deleted.");
			}
		} catch (Exception e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error while deleting Scheme Code.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + "=>" + audit.getStackTrace());
		}
		audit.setTxnStatus(response.getStatus());
		audit.setMessage(response.getDescription());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		ftpAuditService.logAudit(audit);
		LOG.debug("End deleteScheme");
		return response;
	}

	public CustomResponse updateScheme(SchemeCodeBean scheme,User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start updateScheme");
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_UPDATE);
		audit.setTxnUser(user.getUsername());
		audit.setBankCode(user.getEntity());
		audit.setTxnObjectType(KwtFtpSchemes.class.getName());

		try {
			KwtFtpSchemes schemeCode = schemeCodesRepository.findOne(scheme.getSchemeId());
			if (schemeCode == null) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Scheme Code does not exists.");
			} else {
				audit.setPreTxnVal(ftpAuditService.objectToJson(schemeCode));
				schemeCode.setSchmCode(scheme.getSchemeCode());
				schemeCode.setSchmDesc(scheme.getSchemeDesc());
				schemeCode.setGlSubHeadCode(scheme.getGlSubHeadCode());
				schemeCode.setFtpCategory(scheme.getFtpCategory());
				schemeCode.setBankCode(user.getEntity());
				schemeCode.setLastUpdatedDate(ftpAuditService.getCurrentTime());
				schemeCode.setUpdatedBy(user.getUsername());
				schemeCode = schemeCodesRepository.save(schemeCode);
				audit.setPostTxnVal(ftpAuditService.objectToJson(schemeCode));
				response.setStatus(Constants.STATUS_OK);
				response.setDescription("Scheme Code Updated.");
			}
		} catch (Exception e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error while updating Scheme Code.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + "=>" + audit.getStackTrace());
		}
		audit.setTxnStatus(response.getStatus());
		audit.setMessage(response.getDescription());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		ftpAuditService.logAudit(audit);
		LOG.debug("End updateScheme");
		return response;
	}

	public Pageable createPageRequest(Integer currentPage, Integer pageSize) {
		return (new PageRequest(currentPage, pageSize, new Sort("schmDesc")));
	}

	public Page<KwtFtpSchemes> getPagedSchemes(String searchString, Pageable pageData,User user) {
		Page<KwtFtpSchemes> pagedSchemes = null;
		LOG.debug("Start getPagedSchemes");
		try {
			pagedSchemes = schemeCodesRepository
				.findByBankCodeAndSchmCodeContainingOrSchmDescContainingOrGlSubHeadCodeContainingOrFtpCategoryContaining(
						user.getEntity(),searchString, searchString, searchString, searchString, pageData);
		} catch(Exception e) {
			LOG.error("Error in paging schemes =>"+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getPagedSchemes");
		return pagedSchemes;
	}
}
