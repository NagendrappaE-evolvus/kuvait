package com.evolvus.abk.ftp.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.constants.KeyRateConstants;
import com.evolvus.abk.ftp.controller.FileController;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.domain.rates.KeyRates;
import com.evolvus.abk.ftp.repository.KeyRateRepository;
import com.evolvus.abk.ftp.service.RateService;

@Service
@Qualifier(value = "KeyRateFileService")
public class KeyRateFileService implements RateService {

	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	KeyRateRepository keyRateRepository;

	@Override
	public CustomResponse uploadRates(String fileType, FileInfo fileInfo, String date, User user, Boolean overwrite){

		CustomResponse response = new CustomResponse();
		FileInputStream excelFile = null;
		Workbook workbook = null;
		FtpAudit audit = new FtpAudit();
		java.sql.Date sqlDate = null;
		Sheet datatypeSheet = null;
		Row currentRow = null;
		LOG.debug("Start key uploadRates");
		try {

			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
			sqlDate = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
			Iterator<Row> rowIterator;
			datatypeSheet = workbook.getSheet("Static_Rates_Data");
			rowIterator = datatypeSheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			int temp = 0;
			if (rowIterator.hasNext()) {
				rowIterator.next();
				while (rowIterator.hasNext()) {
					currentRow = rowIterator.next();
					KeyRates keyrate = new KeyRates();
					keyrate.setBusinessCloseDate(sqlDate);
					keyrate.setBankCode(user.getEntity());
					Cell currentCell = currentRow.getCell(0);
					if (currentCell.getCellType() == Cell.CELL_TYPE_STRING)
						keyrate.setTenor(currentCell.getStringCellValue());
					else
						throw new IllegalStateException();
					
					currentCell = currentRow.getCell(1);
					if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						keyrate.setKeyRate(new BigDecimal(dataFormatter.formatCellValue(currentCell)));
					}
					else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING)
						keyrate.setKeyRate(BigDecimal.valueOf(Double.parseDouble(currentCell.getStringCellValue())));
					else
						throw new IllegalStateException();

					keyrate.setCurrency(KeyRateConstants.KEY_RATES_HEADERS.get(temp).get("ccy"));
					keyrate.setUploadedBy(user.getUsername());
					keyrate.setUploadedDate(new Date());
					if (overwrite) {
						keyrate.setOverwrittenBy(user.getUsername());
						keyrate.setOverwrittenDate(new Date());
					}
					temp++;
					keyRateRepository.save(keyrate);
				}
				response.setDescription("File uploaded successfully");
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setDescription("No records to read.");
				response.setStatus(Constants.STATUS_NO_DATA);
			}

		}catch (EncryptedDocumentException e) {
			response.setDescription("Unable to read Encrypted document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IllegalStateException e) {
			response.setDescription("Unable to parse data, error while processing in sheet "
					+ datatypeSheet.getSheetName() + ", in row number " + currentRow.getRowNum());
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (FileNotFoundException e) {
			response.setDescription("Error: File not found.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IOException e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("I/O error.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (Exception e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		}
		if (response.getStatus().equals(Constants.STATUS_FAIL)) {
			fileUploadService.deleteExistingRecords(fileType, date, user);
		}

		 audit.setMessage(response.getDescription());
		 audit.setTxnStatus(response.getStatus());
		 audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		 audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		 ftpAuditService.logAudit(audit);

		LOG.debug("End key uploadRates");
		return response;

	}

}
