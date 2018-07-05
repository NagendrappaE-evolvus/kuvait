package com.evolvus.abk.ftp.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.constants.CcyCurveConstants;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.constants.KeyRateConstants;
import com.evolvus.abk.ftp.constants.MarginAdjustmentConstants;
import com.evolvus.abk.ftp.constants.MarginCurveExtConstants;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.CurrencyRateRepository;
import com.evolvus.abk.ftp.repository.KeyRateRepository;

@Service
public class FileUploadService
		implements CcyCurveConstants, KeyRateConstants, MarginAdjustmentConstants, MarginCurveExtConstants {

	
	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	CurrencyRateRepository currencyRateRepository;

	@Autowired
	KeyRateRepository keyRateRepository;

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadService.class);

	@Transactional
	public Long deleteExistingRecords(String fileType, String date, User user) {
		Long recordsDeleted = 0L;
		LOG.debug("Start deleteExistingRecords.");
		try {
			java.sql.Date applyDate = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());

			if ("Currency Rates".equals(fileType)) {
				recordsDeleted = currencyRateRepository.deleteInBulkByBusinessCloseDateAndBankCode(applyDate,
						user.getEntity());
			} else if ("Static Rates".equals(fileType)) {
				recordsDeleted = keyRateRepository.deleteInBulkByBusinessCloseDateAndBankCode(applyDate,
						user.getEntity());
			}
		} catch (ParseException e) {
			LOG.error("Error in delete records: " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End deleteExistingRecords.");
		return recordsDeleted;
	}

	public CustomResponse dataExistingCount(String fileType, String date, User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start dataExistingCount.");
		try {
			java.sql.Date applyDate = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
			Long recordsFound = 0L;
			if ("Currency Rates".equalsIgnoreCase(fileType)) {
				recordsFound = currencyRateRepository.countByBusinessCloseDateAndBankCode(applyDate, user.getEntity());
			} else if ("Static Rates".equalsIgnoreCase(fileType)) {
				recordsFound = keyRateRepository.countByBusinessCloseDateAndBankCode(applyDate, user.getEntity());
			}
			response.setStatus(Constants.STATUS_OK);
			response.setData(recordsFound);
		} catch (ParseException e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error in fetching da.");
			LOG.error("Error in count check : " + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End dataExistingCount.");
		return response;
	}

	public Sheet getSheetByName(Workbook workbook, String name) {
		return null;
	}

	public Sheet getSheetByName(Workbook workbook, Integer index) {
		return null;
	}

}