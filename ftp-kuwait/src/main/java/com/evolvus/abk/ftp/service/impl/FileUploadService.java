package com.evolvus.abk.ftp.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.constants.CcyCurveConstants;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.constants.KeyRateConstants;
import com.evolvus.abk.ftp.constants.MarginAdjustmentConstants;
import com.evolvus.abk.ftp.constants.MarginCurveExtConstants;
import com.evolvus.abk.ftp.domain.AllKeyRates;
import com.evolvus.abk.ftp.domain.CurrencyCurveRates;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.ItemMaster;
import com.evolvus.abk.ftp.domain.MarginAdjstTemplate;
import com.evolvus.abk.ftp.domain.MarginCurveExtd;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.AllKeyRatesRepository;
import com.evolvus.abk.ftp.repository.CcyCurveRateRepository;
import com.evolvus.abk.ftp.repository.CurrencyRateRepository;
import com.evolvus.abk.ftp.repository.ItemRepository;
import com.evolvus.abk.ftp.repository.KeyRateRepository;
import com.evolvus.abk.ftp.repository.MrgnAdjstRatesRepository;
import com.evolvus.abk.ftp.repository.MrgnCurveExtdRepository;

@Service
public class FileUploadService
		implements CcyCurveConstants, KeyRateConstants, MarginAdjustmentConstants, MarginCurveExtConstants {

	@Autowired
	CcyCurveRateRepository ccyCurveRateRepository;

	@Autowired
	AllKeyRatesRepository allKeyRatesRepository;

	@Autowired
	MrgnAdjstRatesRepository mrgnAdjstRatesRepository;

	@Autowired
	MrgnCurveExtdRepository mrgnCurveExtdRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	FtpAuditService ftpAuditService;
	
	@Autowired
	CurrencyRateRepository currencyRateRepository;
	
	@Autowired
	KeyRateRepository keyRateRepository;

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadService.class);

	public CustomResponse uploadCurveRates(String fileType, FileInfo fileInfo, String date, Boolean overwrite,
			User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start uploadCurveRates");

		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(CurrencyCurveRates.class.getName());
		audit.setBankCode(user.getEntity());
		audit.setTxnUser(user.getUsername());

		try {
			FileInputStream excelFile = new FileInputStream(fileInfo.getUploadedFile());
			Workbook workbook = WorkbookFactory.create(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			Calendar rateAppDate = Calendar.getInstance();
			rateAppDate.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(date));
			fileInfo.setRateApplicableDate(rateAppDate.getTime());

			java.sql.Date uploadDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			fileInfo.setUploadDate(uploadDate);
			if (overwrite && rowIterator.hasNext()) {
				deleteExistingRecords(fileType, date, user);
			}
			DataFormatter dataFormatter = new DataFormatter();
			
			List<CurrencyCurveRates> curveRates = new ArrayList<CurrencyCurveRates>();
			if (rowIterator.hasNext()) {
				while (rowIterator.hasNext()) {
					CurrencyCurveRates curveRate = null;
					Row currentRow = rowIterator.next();
					Iterator<Cell> cellIterator = currentRow.cellIterator();
					Cell firstCell = cellIterator.next();
					Calendar rateAppDateInFile = Calendar.getInstance();
					String ccyCode = null;
					if (firstCell.getCellTypeEnum() == CellType.STRING && firstCell.getStringCellValue() != null) {
						ccyCode = firstCell.getStringCellValue().trim();
					} else {
						throw new InvalidFormatException("Currency does not exist.");
					}
					rateAppDateInFile = rateAppDate;
					Integer cellIndex = ONE;
					Map<String, String> ccyRateConf = null;
					while (cellIterator.hasNext() && cellIndex <= CCY_CRV_MAX_CELL + ONE) {
						curveRate = new CurrencyCurveRates();
						Cell currentCell = cellIterator.next();
						if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							curveRate.setBaseRate(new BigDecimal(dataFormatter.formatCellValue(currentCell)));
						} else {
							curveRate.setBaseRate(BigDecimal.ZERO);
						}

						ccyRateConf = CCY_CURVE_HEADERS.get(cellIndex - ONE);
						curveRate.setCcyCode(ccyCode);
						curveRate.setRateAppDate(new java.sql.Date(rateAppDate.getTimeInMillis()));
						curveRate.setRateDateInFile(new java.sql.Date(rateAppDateInFile.getTimeInMillis()));
						if ("DAYS".equals(ccyRateConf.get("unit"))) {
							curveRate.setTenorFromDay(Integer.parseInt(ccyRateConf.get("from")));
							curveRate.setTenorToDay(Integer.parseInt(ccyRateConf.get("to")));
						} else {
							curveRate.setTenorFromMonth(Integer.parseInt(ccyRateConf.get("from")));
							curveRate.setTenorToMonth(Integer.parseInt(ccyRateConf.get("to")));
						}
						curveRate.setKeyTnrCodeRef(ccyRateConf.get("keyCodeRef"));
						curveRate.setTenorCode(ccyRateConf.get("code").trim());
						curveRate.setTenorDesc(ccyRateConf.get("desc").trim());
						curveRate.setUploadDate(uploadDate);
						curveRate.setUploadedBy(user.getUsername());
						curveRate.setBankCode(user.getEntity());

						curveRate.setIsOverwritten(overwrite);
						if (overwrite) {
							curveRate.setOverwrittenBy(user.getUsername());
						}

						cellIndex += ONE;
						curveRates.add(curveRate);
					}
					ccyCurveRateRepository.save(curveRates);
					fileInfo.setNoOfRecords(Long.valueOf(curveRates.size()));
				}
				response.setDescription("File uploaded successfully");
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setDescription("No records to read.");
				response.setStatus(Constants.STATUS_OK);
			}
		} catch (FileNotFoundException e) {
			response.setDescription("Error: File not found.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IOException e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("I/O error.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (EncryptedDocumentException e) {
			response.setDescription("Unable to read Encrypted document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (ParseException | IllegalStateException e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (Exception e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		}
		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);

		LOG.debug("End uploadCurveRates");
		return response;
	}

	public CustomResponse uploadKeyRates(String fileType, FileInfo fileInfo, String date, Boolean overwrite,
			User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start uploadKeyRates");
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(AllKeyRates.class.getName());
		audit.setBankCode(user.getEntity());
		audit.setTxnUser(user.getUsername());

		try {
			List<ItemMaster> currencies = itemRepository.findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc("ccy",
					user.getEntity());
			FileInputStream excelFile = new FileInputStream(fileInfo.getUploadedFile());
			Workbook workbook = WorkbookFactory.create(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			Calendar rateAppDate = Calendar.getInstance();
			rateAppDate.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(date)) 	;
			fileInfo.setRateApplicableDate(rateAppDate.getTime());

			java.sql.Date uploadDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			fileInfo.setUploadDate(uploadDate);

			if (overwrite && rowIterator.hasNext()) {
				deleteExistingRecords(fileType, date, user);
			}

			DataFormatter dataFormatter = new DataFormatter();
			List<AllKeyRates> keyRates = new ArrayList<AllKeyRates>();
			if (rowIterator.hasNext()) {
				while (rowIterator.hasNext()) {
					AllKeyRates keyRate = null;
					Row currentRow = rowIterator.next();
					Iterator<Cell> cellIterator = currentRow.cellIterator();
					/*
					 * Cell firstCell = cellIterator.next();
					 * 
					 * if (firstCell.getCellTypeEnum() == CellType.NUMERIC) { String cellStringValue
					 * = dataFormatter.formatCellValue(firstCell); rateAppDateInFile .setTime(new
					 * SimpleDateFormat(EXCEL_DATE_READER_FORMAT).parse(cellStringValue)); }
					 */
					Calendar rateAppDateInFile = Calendar.getInstance();
					rateAppDateInFile = rateAppDate;

					Integer cellIndex = 0;
					Map<String, String> ccyRateConf = null;
					while (cellIterator.hasNext() && cellIndex <= KEY_RATE_MAX_CELL) {
						keyRate = new AllKeyRates();
						Cell currentCell = cellIterator.next();
						if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							keyRate.setKeyRate(new BigDecimal(dataFormatter.formatCellValue(currentCell)));
						} else {
							keyRate.setKeyRate(BigDecimal.ZERO);
						}

						ccyRateConf = KEY_RATES_HEADERS.get(cellIndex);
						keyRate.setCcyCode(ccyRateConf.get("ccy"));
						keyRate.setRateAppDate(new java.sql.Date(rateAppDate.getTimeInMillis()));
						keyRate.setRateDateInFile(new java.sql.Date(rateAppDateInFile.getTimeInMillis()));
						keyRate.setRateType(ccyRateConf.get("type").trim());
						keyRate.setTenorCode(ccyRateConf.get("code").trim());
						keyRate.setTenorDesc(ccyRateConf.get("desc").trim());

						keyRate.setTenorFrom(Integer.parseInt(ccyRateConf.get("from")));
						keyRate.setTenorTo(Integer.parseInt(ccyRateConf.get("to")));
						keyRate.setTenorFromUnit(ccyRateConf.get("fromUnit"));
						keyRate.setTenorToUnit(ccyRateConf.get("toUnit"));
						keyRate.setUploadDate(uploadDate);
						keyRate.setUploadedBy(user.getUsername());
						keyRate.setBankCode(user.getEntity());
						keyRate.setIsOverwritten(overwrite);
						if (overwrite) {
							keyRate.setOverwrittenBy(user.getUsername());
						}
						if ("FCY".equals(keyRate.getCcyCode()) || "ANY".equals(keyRate.getCcyCode())) {
							for (ItemMaster ccy : currencies) {
								if (!"OTH".equals(ccy.getItemCode())) {
									AllKeyRates ccyKeyRate = (AllKeyRates) keyRate.clone();
									if ("FCY".equals(keyRate.getCcyCode()) && "KWD".equals(ccy.getItemCode()))
										continue;
									ccyKeyRate.setCcyCode(ccy.getItemCode());
									keyRates.add(ccyKeyRate);
								}
							}
						} else {
							keyRates.add(keyRate);
						}
						cellIndex += ONE;
					}
					allKeyRatesRepository.save(keyRates);
					fileInfo.setNoOfRecords(Long.valueOf(keyRates.size()));
				}
				response.setDescription("File uploaded successfully");
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setDescription("No records to read.");
				response.setStatus(Constants.STATUS_OK);
			}
		} catch (FileNotFoundException e) {
			response.setDescription("Error: File not found.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IOException e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("I/O error.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (EncryptedDocumentException e) {
			response.setDescription("Unable to read Encrypted document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (ParseException | IllegalStateException e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (Exception e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		}
		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);

		LOG.debug("End uploadKeyRates");
		return response;
	}

	public CustomResponse uploadMarginAdjustmentRates(String fileType, FileInfo fileInfo, String date,
			Boolean overwrite, User user) {
		CustomResponse response = new CustomResponse();
		LOG.debug("Start uploadMarginAdjustmentRates");

		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(MarginAdjstTemplate.class.getName());
		audit.setBankCode(user.getEntity());
		audit.setTxnUser(user.getUsername());
		
		try {
			List<ItemMaster> currencies = itemRepository.findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc("ccy",
					user.getEntity());
			FileInputStream excelFile = new FileInputStream(fileInfo.getUploadedFile());
			Workbook workbook = WorkbookFactory.create(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			Calendar rateAppDate = Calendar.getInstance();
			rateAppDate.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(date));
			fileInfo.setRateApplicableDate(rateAppDate.getTime());

			java.sql.Date uploadDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			fileInfo.setUploadDate(uploadDate);

			if (overwrite && rowIterator.hasNext()) {
				deleteExistingRecords(fileType, date, user);
			}
			
			DataFormatter dataFormatter = new DataFormatter();
			List<MarginAdjstTemplate> mrgnAdjstRates = new ArrayList<MarginAdjstTemplate>();

			if (rowIterator.hasNext()) {
				while (rowIterator.hasNext()) {
					MarginAdjstTemplate mrgnAdjstRate = null;
					Row currentRow = rowIterator.next();
					Iterator<Cell> cellIterator = currentRow.cellIterator();
					Cell firstCell = cellIterator.next();
					Calendar rateAppDateInFile = Calendar.getInstance();
					if (firstCell.getCellTypeEnum() == CellType.NUMERIC) {
						String cellStringValue = dataFormatter.formatCellValue(firstCell);
						/*
						 * rateAppDateInFile .setTime(new
						 * SimpleDateFormat(EXCEL_DATE_READER_FORMAT).parse(cellStringValue));
						 */
					}
					rateAppDateInFile = rateAppDate;
					Integer cellIndex = ONE;
					Map<String, String> ccyRateConf = null;
					while (cellIterator.hasNext() && cellIndex <= MRGN_ADJ_MAX_CELL + ONE) {
						mrgnAdjstRate = new MarginAdjstTemplate();
						Cell currentCell = cellIterator.next();
						if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							mrgnAdjstRate.setMrgnAdjRate(new BigDecimal(dataFormatter.formatCellValue(currentCell)));
						} else {
							mrgnAdjstRate.setMrgnAdjRate(BigDecimal.ZERO);
						}

						ccyRateConf = ADJSTMNT_TEMP_HEADERS.get(cellIndex - ONE);
						mrgnAdjstRate.setCcyCode(ccyRateConf.get("ccy"));
						mrgnAdjstRate.setRateAppDate(new java.sql.Date(rateAppDate.getTimeInMillis()));
						mrgnAdjstRate.setRateDateInFile(new java.sql.Date(rateAppDateInFile.getTimeInMillis()));
						mrgnAdjstRate.setRateType(ccyRateConf.get("type").trim());
						mrgnAdjstRate.setTenorCode(ccyRateConf.get("code").trim());
						mrgnAdjstRate.setTenorDesc(ccyRateConf.get("desc").trim());

						mrgnAdjstRate.setTenorFrom(Integer.parseInt(ccyRateConf.get("from")));
						mrgnAdjstRate.setTenorTo(Integer.parseInt(ccyRateConf.get("to")));
						mrgnAdjstRate.setTenorFromUnit(ccyRateConf.get("fromUnit"));
						mrgnAdjstRate.setTenorToUnit(ccyRateConf.get("toUnit"));
						mrgnAdjstRate.setUploadDate(uploadDate);
						mrgnAdjstRate.setUploadedBy(user.getUsername());
						mrgnAdjstRate.setBankCode(user.getEntity());
						mrgnAdjstRate.setIsOverwritten(overwrite);
						
						if(overwrite) {
							mrgnAdjstRate.setOverwrittenBy(user.getUsername());
						}

						cellIndex += ONE;

						if ("FCY".equals(mrgnAdjstRate.getCcyCode()) || "ANY".equals(mrgnAdjstRate.getCcyCode())) {
							for (ItemMaster ccy : currencies) {
								if (!"OTH".equals(ccy.getItemCode())) {
									MarginAdjstTemplate ccyMrgnAdjstRate = (MarginAdjstTemplate) mrgnAdjstRate.clone();
									if ("FCY".equals(mrgnAdjstRate.getCcyCode()) && "KWD".equals(ccy.getItemCode()))
										continue;
									ccyMrgnAdjstRate.setCcyCode(ccy.getItemCode());
									mrgnAdjstRates.add(ccyMrgnAdjstRate);
								}
							}
						} else {
							mrgnAdjstRates.add(mrgnAdjstRate);
						}

					}
					mrgnAdjstRatesRepository.save(mrgnAdjstRates);
					fileInfo.setNoOfRecords(Long.valueOf(mrgnAdjstRates.size()));
				}
				response.setDescription("File uploaded successfully");
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setDescription("No records to read.");
				response.setStatus(Constants.STATUS_OK);
			}
		} catch (FileNotFoundException e) {
			response.setDescription("Error: File not found.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IOException e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("I/O error.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (EncryptedDocumentException e) {
			response.setDescription("Unable to read Encrypted document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (ParseException | IllegalStateException e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (Exception e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		}
		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);

		LOG.debug("End uploadKeyRates");
		return response;
	}

	public CustomResponse uploadMarginCurveExtendedRates(String fileType, FileInfo fileInfo, String date,
			Boolean overwrite, User user) {
		CustomResponse response = new CustomResponse();

		LOG.debug("Start uploadMarginCurveExtendedRates");

		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(MarginCurveExtd.class.getName());
		audit.setBankCode(user.getEntity());
		audit.setTxnUser(user.getUsername());
		try {
			List<ItemMaster> currencies = itemRepository.findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc("ccy",
					user.getEntity());
			FileInputStream excelFile = new FileInputStream(fileInfo.getUploadedFile());
			Workbook workbook = WorkbookFactory.create(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			Calendar rateAppDate = Calendar.getInstance();
			rateAppDate.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(date));
			if(overwrite && rowIterator.hasNext()) {
				deleteExistingRecords(fileType, date, user);
			}
			java.sql.Date uploadDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

			DataFormatter dataFormatter = new DataFormatter();
			List<MarginCurveExtd> mrgnCurveExtdRates = new ArrayList<MarginCurveExtd>();

			if (rowIterator.hasNext()) {
				while (rowIterator.hasNext()) {
					MarginCurveExtd mrgnCurveExtdRate = null;
					Row currentRow = rowIterator.next();
					Iterator<Cell> cellIterator = currentRow.cellIterator();
					Cell firstCell = cellIterator.next();
					Calendar rateAppDateInFile = Calendar.getInstance();
					if (firstCell.getCellTypeEnum() == CellType.NUMERIC) {
						String cellStringValue = dataFormatter.formatCellValue(firstCell);
						/*
						 * rateAppDateInFile .setTime(new
						 * SimpleDateFormat(EXCEL_DATE_READER_FORMAT).parse(cellStringValue));
						 */
					}
					rateAppDateInFile = rateAppDate;
					Integer cellIndex = ONE;
					Map<String, String> ccyRateConf = null;
					while (cellIterator.hasNext() && cellIndex <= MRGN_ADJ_MAX_CELL + ONE) {
						mrgnCurveExtdRate = new MarginCurveExtd();
						Cell currentCell = cellIterator.next();
						if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							mrgnCurveExtdRate
									.setMrgnCrvExRate(new BigDecimal(dataFormatter.formatCellValue(currentCell)));
						} else {
							mrgnCurveExtdRate.setMrgnCrvExRate(BigDecimal.ZERO);
						}

						ccyRateConf = ADJSTMNT_TEMP_HEADERS.get(cellIndex - ONE);
						mrgnCurveExtdRate.setCcyCode(ccyRateConf.get("ccy"));
						mrgnCurveExtdRate.setRateAppDate(new java.sql.Date(rateAppDate.getTimeInMillis()));
						mrgnCurveExtdRate.setRateDateInFile(new java.sql.Date(rateAppDateInFile.getTimeInMillis()));
						mrgnCurveExtdRate.setRateType(ccyRateConf.get("type").trim());
						mrgnCurveExtdRate.setTenorCode(ccyRateConf.get("code").trim());
						mrgnCurveExtdRate.setTenorDesc(ccyRateConf.get("desc").trim());

						mrgnCurveExtdRate.setTenorFrom(Integer.parseInt(ccyRateConf.get("from")));
						mrgnCurveExtdRate.setTenorTo(Integer.parseInt(ccyRateConf.get("to")));
						mrgnCurveExtdRate.setTenorFromUnit(ccyRateConf.get("fromUnit"));
						mrgnCurveExtdRate.setTenorToUnit(ccyRateConf.get("toUnit"));
						mrgnCurveExtdRate.setUploadDate(uploadDate);
						mrgnCurveExtdRate.setUploadedBy(user.getUsername());
						mrgnCurveExtdRate.setBankCode(user.getEntity());
						mrgnCurveExtdRate.setIsOverwritten(overwrite);
						
						if(overwrite) {
							mrgnCurveExtdRate.setOverwrittenBy(user.getUsername());
						}
						
						cellIndex += ONE;
						if ("FCY".equals(mrgnCurveExtdRate.getCcyCode())
								|| "ANY".equals(mrgnCurveExtdRate.getCcyCode())) {
							for (ItemMaster ccy : currencies) {
								if (!"OTH".equals(ccy.getItemCode())) {
									MarginCurveExtd ccyMrgnCurveExtdRate = (MarginCurveExtd) mrgnCurveExtdRate.clone();
									if ("FCY".equals(mrgnCurveExtdRate.getCcyCode()) && "KWD".equals(ccy.getItemCode()))
										continue;
									ccyMrgnCurveExtdRate.setCcyCode(ccy.getItemCode());
									mrgnCurveExtdRates.add(ccyMrgnCurveExtdRate);
								}
							}
						} else {
							mrgnCurveExtdRates.add(mrgnCurveExtdRate);
						}
					}
					fileInfo.setNoOfRecords(Long.valueOf(mrgnCurveExtdRates.size()));
					mrgnCurveExtdRepository.save(mrgnCurveExtdRates);
				}
				response.setDescription("File uploaded successfully");
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setDescription("No records to read.");
				response.setStatus(Constants.STATUS_OK);
			}
		} catch (FileNotFoundException e) {
			response.setDescription("Error: File not found.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IOException e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("I/O error.");
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (EncryptedDocumentException e) {
			response.setDescription("Unable to read Encrypted document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (ParseException | IllegalStateException e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (Exception e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		}
		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);

		LOG.debug("End uploadMarginCurveExtendedRates");

		return response;
	}

	@Transactional
	public Long deleteExistingRecords(String fileType, String date, User user) {
		Long recordsDeleted = 0L;
		LOG.debug("Start deleteExistingRecords.");
		try {
			java.sql.Date applyDate = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());

			if("Currency Rates".equals(fileType)) {
				recordsDeleted = currencyRateRepository.deleteInBulkByBusinessCloseDateAndBankCode(applyDate, user.getEntity());
			}else if("Static Rates".equals(fileType)) {
				recordsDeleted = keyRateRepository.deleteInBulkByBusinessCloseDateAndBankCode(applyDate, user.getEntity());
			}else if("Yield Curve".equals(fileType)) {
				recordsDeleted = ccyCurveRateRepository.deleteInBulkByRateDateInFileAndBankCode(applyDate, user.getEntity());
			} else if ("All Key Rates".equals(fileType)) {
				recordsDeleted = allKeyRatesRepository.deleteInBulkByRateDateInFileAndBankCode(applyDate, user.getEntity());
			} else if ("Margin Adjustment".equals(fileType)) {
				recordsDeleted = mrgnAdjstRatesRepository.deleteInBulkByRateDateInFileAndBankCode(applyDate,
						user.getEntity());
			} else if ("Margin Curve Extended".equals(fileType)) {
				recordsDeleted = mrgnCurveExtdRepository.deleteInBulkByRateDateInFileAndBankCode(applyDate, user.getEntity());
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
			if ("Yield Curve".equals(fileType)) {
				recordsFound = ccyCurveRateRepository.countByRateDateInFileAndBankCode(applyDate, user.getEntity());
			} else if ("All Key Rates".equals(fileType)) {
				recordsFound = allKeyRatesRepository.countByRateDateInFileAndBankCode(applyDate, user.getEntity());
			} else if ("Margin Adjustment".equals(fileType)) {
				recordsFound = mrgnAdjstRatesRepository.countByRateDateInFileAndBankCode(applyDate, user.getEntity());
			} else if ("Margin Curve Extended".equals(fileType)) {
				recordsFound = mrgnCurveExtdRepository.countByRateDateInFileAndBankCode(applyDate, user.getEntity());
			}else if("Currency Rates".equalsIgnoreCase(fileType)) {
				recordsFound = currencyRateRepository.countByBusinessCloseDateAndBankCode(applyDate, user.getEntity());
			}else if("Static Rates".equalsIgnoreCase(fileType)) {
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
	
	public Sheet getSheetByName(Workbook workbook,String name) {
		return null;
	}
	
	public Sheet getSheetByName(Workbook workbook,Integer index) {
		return null;
	}
	
}