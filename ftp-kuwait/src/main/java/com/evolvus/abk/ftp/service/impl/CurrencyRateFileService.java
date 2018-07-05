package com.evolvus.abk.ftp.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import com.evolvus.abk.ftp.controller.FileController;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.domain.rates.CurrencyRates;
import com.evolvus.abk.ftp.repository.CurrencyRateRepository;
import com.evolvus.abk.ftp.service.RateService;

@Service
@Qualifier(value = "CurrencyRateFileService")
public class CurrencyRateFileService implements RateService {

	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	CurrencyRateRepository currencyRateRepository;

	@Autowired
	FileUploadService fileUploadService;

	@Override
	public CustomResponse uploadRates(String fileType, FileInfo fileInfo, String date, User user, Boolean overwrite) {

		CustomResponse response = new CustomResponse();
		FileInputStream excelFile = null;
		Workbook workbook = null;
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(CurrencyRates.class.getName());
		audit.setBankCode(user.getEntity());
		audit.setTxnUser(user.getUsername());
		java.sql.Date sqlDate = null;
		Sheet datatypeSheet = null;
		Row currentRow = null;
		LOG.debug("Start Currency uploadRates");
		try {
			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
			sqlDate = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
			Iterator<Row> rowIterator;

			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				datatypeSheet = workbook.getSheetAt(i);
				rowIterator = datatypeSheet.iterator();
				int temp = 0;
				if (rowIterator.hasNext()) {
					currentRow = rowIterator.next();
					while (rowIterator.hasNext()) {
						currentRow = rowIterator.next();
						if (currentRow.getRowNum() < 3)
							continue;
						CurrencyRates curRates = new CurrencyRates();

						Cell cell = currentRow.getCell(0);
						String tenorDays = cell.getStringCellValue();
						Map<String,Integer> tenorDaysMap = this.getTenorBucketInDays(tenorDays);
						curRates.setDaysFrom(tenorDaysMap.get(Constants.FROM));
						curRates.setDaysTo(tenorDaysMap.get(Constants.TO));

						cell = currentRow.getCell(1);
						curRates.setCurrency(datatypeSheet.getSheetName());

						curRates.setBusinessCloseDate(sqlDate);
						if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
							curRates.setTenor(cell.getStringCellValue().trim());
						} else {
							curRates.setTenor("");
						}

						cell = currentRow.getCell(2);
						if (cell.getCellTypeEnum() == CellType.NUMERIC) {
							curRates.setBase(BigDecimal.valueOf(cell.getNumericCellValue()));
						} else {
							curRates.setBase(BigDecimal.ZERO);
						}

						cell = currentRow.getCell(3);
						if (cell.getCellTypeEnum() == CellType.NUMERIC) {
							curRates.setMargin(BigDecimal.valueOf(cell.getNumericCellValue()));
						} else {
							curRates.setMargin(BigDecimal.ZERO);
						}

						cell = currentRow.getCell(4);
						if (cell.getCellTypeEnum() == CellType.NUMERIC) {
							curRates.setNet(BigDecimal.valueOf(cell.getNumericCellValue()));
						} else {
							curRates.setNet(BigDecimal.ZERO);
						}
						curRates.setBankCode(user.getEntity());
						if (overwrite) {
							curRates.setOverwrittenBy(user.getUsername());
							curRates.setOverwrittenDate(new Date());
						}
						curRates.setUploadedBy(user.getUsername());
						curRates.setUploadedDate(new Date());
						currencyRateRepository.save(curRates);
						temp++;
					}
					response.setDescription("File uploaded successfully");
					response.setStatus(Constants.STATUS_OK);
				} else {
					response.setDescription("No records to read.");
					response.setStatus(Constants.STATUS_NO_DATA);
				}
			}
		} catch (FileNotFoundException e) {
			response.setDescription("Error: File not found.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (EncryptedDocumentException e) {
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
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (response.getStatus().equals(Constants.STATUS_FAIL)) {
			fileUploadService.deleteExistingRecords(fileType, date, user);
		}
		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);
		LOG.debug("End Currency uploadRates");
		return response;

	}

	@Override
	public Map<String, Integer> getTenorBucketInDays(String tenor) {
		
		Map<String, Integer> tenorBucket = new HashMap<>();
		String from = "";
		String to = "";
		String[] rawArray = null;
		if (tenor != null && !tenor.trim().isEmpty()) {
			String lowerCaseTenor = tenor.toLowerCase();
			rawArray = this.getSplittedStringArray(tenor, Constants.NON_NUMERIC_PATTERN);
			
			if (lowerCaseTenor.contains(Constants.STR_ABOVE.toLowerCase())) {
				if (rawArray.length == 1) {
					from = rawArray[0];
					to = rawArray[0];
				}
			} else if (lowerCaseTenor.contains(Constants.STR_UPTO.toLowerCase())) {
				if (rawArray.length == 1) {
					to = rawArray[0];
				}
			} else {
				if (rawArray.length == 2) {
					from = rawArray[0];
					to = rawArray[1];
				}
			}
		}
		
		if(!from.isEmpty()) {
			tenorBucket.put(Constants.FROM, Integer.parseInt(from));
		} else {
			tenorBucket.put(Constants.FROM, 0);
		}
		
		if(!to.isEmpty()) {
			tenorBucket.put(Constants.TO, Integer.parseInt(to));
		} else {
			tenorBucket.put(Constants.TO, 0);
		}
		return tenorBucket;

	}

	@Override
	public String[] getSplittedStringArray(String str, String pattern) {
		return Arrays.stream(str.split(pattern)).filter(value -> value != null && value.length() > 0)
				.toArray(size -> new String[size]);

	}
}
