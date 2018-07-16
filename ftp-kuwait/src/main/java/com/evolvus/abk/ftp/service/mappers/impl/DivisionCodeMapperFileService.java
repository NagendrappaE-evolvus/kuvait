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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
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
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPDivisionCodeMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPDivisionCodeMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPDivisionCodeMapperTemp;
import com.evolvus.abk.ftp.repository.mappers.main.DivisionCodeMapperRepository;
import com.evolvus.abk.ftp.repository.mappers.main.archivals.DivisionCodeMapperArchiveRepository;
import com.evolvus.abk.ftp.repository.mappers.temp.DivisionCodeMapperTempRepository;
import com.evolvus.abk.ftp.service.MapperFileService;
import com.evolvus.abk.ftp.service.MapperVersionService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.MapperConversionService;

@Service
@Qualifier(value = "DivisionCodeMapperService")
public class DivisionCodeMapperFileService implements MapperFileService {

	private static final Logger LOG = LoggerFactory.getLogger(DivisionCodeMapperFileService.class);

	@Autowired
	FtpAuditService ftpAuditService;

	@Autowired
	MapperConversionService mapperConversionService;

	@Autowired
	MapperVersionService mapperVersionService;

	@Autowired
	DivisionCodeMapperTempRepository divisionMapperTempRepository;

	@Autowired
	DivisionCodeMapperRepository divisionMapperMainRepository;

	@Autowired
	DivisionCodeMapperArchiveRepository divisionMapperArchiveRepository;

	@Override
	public CustomResponse uploadToTemp(FileInfo fileInfo, Principal user) {
		LOG.info(" Start uploadToTemp ");
		User appUser = ftpAuditService.getUserFromPrincipal(user);
		FileInputStream excelFile = null;
		Workbook workbook = null;
		Cell currentCell = null;
		CustomResponse response = new CustomResponse();
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(FTPDivisionCodeMapperTemp.class.getName());
		audit.setBankCode(appUser.getEntity());
		audit.setTxnUser(appUser.getUsername());
		Row currentRow = null;
		Sheet datatypeSheet = null;

		try {
			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
			datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			FormulaEvaluator evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
			List<FTPDivisionCodeMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				clearRecords(appUser.getEntity());
				rowIterator.next();
				while (rowIterator.hasNext()) {
					currentRow = rowIterator.next();// currentRow.getLastCellNum()
					FTPDivisionCodeMapperTemp mapper = null;
					mapper = new FTPDivisionCodeMapperTemp();

					currentCell = currentRow.getCell(0);
					mapper.setGlSubHeadCode(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(1);
					int temp=mapperConversionService.getNumericCellValue(currentCell,evaluator);
					if(temp!=-111)
					mapper.setGlshChar(temp);

					currentCell = currentRow.getCell(2);
					mapper.setEntityCode(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(3);
					mapper.setCategory(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(4);
					mapper.setDivisionDesc(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(5);
					mapper.setOfficer(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(6);
					mapper.setSubDivision(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(7);
					mapper.setDivision(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(8);
					mapper.setFinalDivisionDesc(mapperConversionService.getStringCellValue(currentCell));
					
					MapperVersion version = mapperVersionService.getMapper("DC");
					mapper.setVersion(version.getVersionChars() + (version.getCurrentVersion()+1));
					mapper.setUploadedDate(new Date());
					mapper.setUploadedBy(user.getName());
					mapper.setBankCode(appUser.getEntity());
					mappersList.add(mapper);

					numberOfRecords += 1;
					fileInfo.setNoOfRecords(numberOfRecords);
				}
				divisionMapperTempRepository.save(mappersList);
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
			int num=currentRow.getRowNum()+1;
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
			int num=currentRow.getRowNum()+1;
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
					e.printStackTrace();
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
		LOG.info(" End uploadToTemp ");
		return response;
	}

	@Override
	@Transactional
	public void clearRecords(FtpEntity ftpEntity) {
		LOG.info(" Start clearRecords ");
		divisionMapperTempRepository.deleteInBulkByBankCode(ftpEntity);
		LOG.info(" End clearRecords ");
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain(FtpEntity ftpEntity) {
		LOG.info(" Start getDifferenceOfTempAndMain ");
		String bankCode = ftpEntity.getBankCode();
		List<String> tempNotInMain = divisionMapperTempRepository.fetchRecordsNotInMain(bankCode);
		List<String> mainNotInTemp = divisionMapperMainRepository.fetchRecordsNotInTemp(bankCode);
		Set<String> glSubheadSet = new HashSet<>();
		glSubheadSet.addAll(tempNotInMain);
		glSubheadSet.addAll(mainNotInTemp);
		List<FTPDivisionCodeMapperTemp> tempNotInMainList = divisionMapperTempRepository
				.findByGlSubHeadCodeInAndBankCodeOrderByGlSubHeadCode(glSubheadSet, ftpEntity);
		List<FTPDivisionCodeMapper> mainNotInTempList = divisionMapperMainRepository
				.findByGlSubHeadCodeInAndBankCodeOrderByGlSubHeadCode(glSubheadSet, ftpEntity);
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
		Iterable<FTPDivisionCodeMapper> mappersList = divisionMapperMainRepository.findByBankCode(ftpEntity);
		List<FTPDivisionCodeMapperArchive> archives = new ArrayList<>();
		mappersList.forEach(mapper -> {
			// divisionMapperMainRepository.delete(mapper);
			archives.add(mapperConversionService.mainToArchive(mapper));
		});
		divisionMapperMainRepository.deleteInBulkByBankCode(ftpEntity);
		if (!archives.isEmpty()) {
			divisionMapperArchiveRepository.save(archives);
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
		Iterable<FTPDivisionCodeMapperTemp> tempMappers = divisionMapperTempRepository.findByBankCode(ftpEntity);
		List<FTPDivisionCodeMapper> mainMappers = new ArrayList<>();
		MapperVersion version = mapperVersionService.getMapper("DC");

		Long nextMainVersion = version.getCurrentVersion() + 1;
		String mainVersion = version.getVersionChars() + nextMainVersion;
		tempMappers.forEach(mapper -> {
			// divisionMapperTempRepository.delete(mapper);
			mapper.setVersion(mainVersion);
			mainMappers.add(mapperConversionService.tempToMain(mapper));
		});
		divisionMapperTempRepository.deleteInBulkByBankCode(ftpEntity);
		if (!mainMappers.isEmpty()) {
			divisionMapperMainRepository.save(mainMappers);
			mapperVersionService.updateMapperVersion("DC", nextMainVersion, version.getCurrentVersion());
			LOG.info(" End insertToMain ");
			return (long) mainMappers.size();
		}
		LOG.info(" End insertToMain ");
		return 0L;
	}
}
