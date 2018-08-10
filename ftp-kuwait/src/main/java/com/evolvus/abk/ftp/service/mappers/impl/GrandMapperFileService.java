package com.evolvus.abk.ftp.service.mappers.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.domain.mappers.MapperVersion;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPGrandMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPGrandMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;
import com.evolvus.abk.ftp.repository.mappers.main.GrandMapperMainRepository;
import com.evolvus.abk.ftp.repository.mappers.main.archivals.GrandMapperArchiveRepository;
import com.evolvus.abk.ftp.repository.mappers.temp.GrandMapperTempRepository;
import com.evolvus.abk.ftp.service.MapperFileService;
import com.evolvus.abk.ftp.service.MapperVersionService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.MapperConversionService;

@Service
@Qualifier(value = "GrandMapperFileService")
public class GrandMapperFileService implements MapperFileService {

	private static final Logger LOG = LoggerFactory.getLogger(GrandMapperFileService.class);

	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	GrandMapperTempRepository grandMapperTempRepository;

	@Autowired
	GrandMapperMainRepository grandMapperMainRepository;

	@Autowired
	GrandMapperArchiveRepository grandMapperArchiveRepository;

	@Autowired
	MapperVersionService mapperVersionService;

	@Autowired
	MapperConversionService mapperConversionService;

	@Override
	public CustomResponse uploadToTemp(FileInfo fileInfo, Principal user) {
		LOG.info(" Start uploadToTemp ");
		User appUser = ftpAuditService.getUserFromPrincipal(user);
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Sheet datatypeSheet = null;
		Row currentRow = null;
		Cell currentCell = null;
		CustomResponse response = new CustomResponse();
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(FTPGrandMapperTemp.class.getName());
		audit.setBankCode(appUser.getEntity());
		audit.setTxnUser(appUser.getUsername());

		try {
			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
			FormulaEvaluator evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
			datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();
			// evaluator.evaluateAll();

			List<FTPGrandMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				// Clearing existing records
				clearRecords(appUser.getEntity());
				while (rowIterator.hasNext()) {
					currentRow = rowIterator.next();
					FTPGrandMapperTemp mapper = null;
					mapper = new FTPGrandMapperTemp();

					currentCell = currentRow.getCell(0);
					mapper.setGlSubheadCode(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(1);
					mapper.setGlSubheadDesc(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(2);
					mapper.setDrFtpCat(mapperConversionService.getStringCellValue(currentCell));
					
					currentCell = currentRow.getCell(3);
					mapper.setCrFtpCat(mapperConversionService.getStringCellValue(currentCell));
					
					currentCell = currentRow.getCell(4);
					mapper.setEntityNoIn(mapperConversionService.getStringCellValue(currentCell));
					
					currentCell = currentRow.getCell(5);
					mapper.setEntityNoNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(6);
					mapper.setUserSubclassCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(7);
					mapper.setUserSubclassCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(8);
					mapper.setBAcidIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(9);
					mapper.setBAcidNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(10);
					mapper.setDivisionCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(11);
					mapper.setDivisionCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(12);
					mapper.setCustInLength(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(13);
					mapper.setCustTypeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(14);
					mapper.setCustNotInLength(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(15);
					mapper.setCustTypeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(16);
					mapper.setSubDivisionCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(17);
					mapper.setSubDivisionCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(18);
					mapper.setTradingBookNameIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(19);
					mapper.setTradingBookNameNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(20);
					mapper.setInstrumentClassIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(21);
					mapper.setInstrumentClassNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(22);
					mapper.setGroupByLogic(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(23);
					mapper.setCount(mapperConversionService.getDoubleValueOfCurrentCell(currentCell, evaluator));

					MapperVersion version = mapperVersionService.getMapper("CT");
					mapper.setVersion(version.getVersionChars() + (version.getCurrentVersion()+1));
					mapper.setUploadedDate(new Date());
					mapper.setUploadedBy(user.getName());
					mapper.setBankCode(appUser.getEntity());
					mappersList.add(mapper);

					numberOfRecords += 1;
					fileInfo.setNoOfRecords(numberOfRecords);
				}
				grandMapperTempRepository.save(mappersList);
				response.setDescription("File uploaded successfully");
				response.setStatus(Constants.STATUS_OK);
			} else {
				response.setDescription("No records to read.");
				response.setStatus(Constants.STATUS_NO_DATA);
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
		} catch (IllegalArgumentException e) {
			int num = currentRow.getRowNum() + 1;
			response.setDescription("Unable to parse data, error while processing in sheet "
					+ datatypeSheet.getSheetName() + ", in row number " + num);
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IllegalStateException e) {
			int num = currentRow.getRowNum() + 1;
			response.setDescription("Unable to parse data, error while processing in sheet "
					+ datatypeSheet.getSheetName() + ", in row number " + num);
			response.setStatus(Constants.STATUS_FAIL);
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
					LOG.error("Not able to close workbook.");
				}
			}
		}

		if (response.getStatus().equals(Constants.STATUS_FAIL)) {
			clearRecords(appUser.getEntity());
		}

		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);
		LOG.info(" Start uploadToTemp ");

		return response;
	}

	@Override
	@Transactional
	public void clearRecords(FtpEntity ftpEntity) {
		LOG.info(" Start clearRecords ");
		grandMapperTempRepository.deleteInBulkByBankCode(ftpEntity);
		LOG.info(" End clearRecords ");
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain(FtpEntity ftpEntity) {
		LOG.info(" Start getDifferenceOfTempAndMain ");
		String bankCode = ftpEntity.getBankCode();
		List<String> tempNotInMain = grandMapperTempRepository.fetchRecordsNotInMain(bankCode);
		List<String> mainNotInTemp = grandMapperMainRepository.fetchRecordsNotInTemp(bankCode);
		Set<String> glSubheadSet = new HashSet<>();
		glSubheadSet.addAll(tempNotInMain);
		glSubheadSet.addAll(mainNotInTemp);
		List<FTPGrandMapperTemp> tempNotInMainList = grandMapperTempRepository
				.findByGlSubheadCodeInAndBankCodeOrderByGlSubheadCode(glSubheadSet, ftpEntity);
		List<FTPGrandMapper> mainNotInTempList = grandMapperMainRepository
				.findByGlSubheadCodeInAndBankCodeOrderByGlSubheadCode(glSubheadSet, ftpEntity);
		Map<String, List<? extends Object>> differences = new HashMap<>();
		differences.put(Constants.LIST_MAIN, mainNotInTempList);
		differences.put(Constants.LIST_TEMP, tempNotInMainList);
		LOG.info(" End getDifferenceOfTempAndMain ");
		return differences;
	}

	@Override
	@Transactional
	public Long archive(FtpEntity ftpEntity) {
		LOG.info(" Start archive ");
		Iterable<FTPGrandMapper> mappersList = grandMapperMainRepository.findByBankCode(ftpEntity);
		List<FTPGrandMapperArchive> archives = new ArrayList<>();
		mappersList.forEach(mapper -> {
			// grandMapperMainRepository.delete(mapper);
			archives.add(mapperConversionService.mainToArchive(mapper));
		});
		grandMapperMainRepository.deleteInBulkByBankCode(ftpEntity);
		if (!archives.isEmpty()) {
			grandMapperArchiveRepository.save(archives);
			LOG.info(" End archive ");
			if (!archives.isEmpty()) {
				return (long) archives.size();
			}
		}
		LOG.info(" End archive ");
		return 0L;
	}

	@Override
	@Transactional
	public Long insertToMain(FtpEntity ftpEntity) {
		LOG.info(" Start insertToMain ");
		Iterable<FTPGrandMapperTemp> tempMappers = grandMapperTempRepository.findByBankCode(ftpEntity);
		List<FTPGrandMapper> mainMappers = new ArrayList<>();
		MapperVersion version = mapperVersionService.getMapper("CT");

		Long nextMainVersion = version.getCurrentVersion() + 1;
		String mainVersion = version.getVersionChars() + nextMainVersion;
		tempMappers.forEach(mapper -> {
			// grandMapperTempRepository.delete(mapper);
			mapper.setVersion(mainVersion);
			mainMappers.add(mapperConversionService.tempToMain(mapper));
		});
		clearRecords(ftpEntity);
		if (!mainMappers.isEmpty()) {
			grandMapperMainRepository.save(mainMappers);
			mapperVersionService.updateMapperVersion("CT", nextMainVersion, version.getCurrentVersion());
			LOG.info(" End insertToMain ");
			return (long) mainMappers.size();
		}
		LOG.info(" End insertToMain ");
		return 0L;
	}
}
