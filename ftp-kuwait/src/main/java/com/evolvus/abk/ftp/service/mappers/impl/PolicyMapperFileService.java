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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPPolicyMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPPolicyMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPPolicyMapperTemp;
import com.evolvus.abk.ftp.repository.mappers.main.PolicyMapperRepository;
import com.evolvus.abk.ftp.repository.mappers.main.archivals.PolicyMapperArchiveRepository;
import com.evolvus.abk.ftp.repository.mappers.temp.PolicyMapperTempRepository;
import com.evolvus.abk.ftp.service.MapperFileService;
import com.evolvus.abk.ftp.service.MapperVersionService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.MapperConversionService;

@Service
@Qualifier(value = "PolicyMapperFileService")
public class PolicyMapperFileService implements MapperFileService {

	private static final Logger LOG = LoggerFactory.getLogger(PolicyMapperFileService.class);

	@Autowired
	private FtpAuditService ftpAuditService;

	@Autowired
	private PolicyMapperTempRepository policyMapperTempRepository;

	@Autowired
	private PolicyMapperRepository policyMapperRepository;

	@Autowired
	private PolicyMapperArchiveRepository policyMapperArchiveRepository;

	@Autowired
	private MapperConversionService mapperConversionService;

	@Autowired
	private MapperVersionService mapperVersionService;

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
			datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();

			List<FTPPolicyMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				// Clearing existing records
				clearRecords(appUser.getEntity());
				rowIterator.next();
				while (rowIterator.hasNext()) {
					currentRow = rowIterator.next();
					FTPPolicyMapperTemp mapper = null;
					mapper = new FTPPolicyMapperTemp();

					currentCell = currentRow.getCell(0);
					mapper.setFtpCategory(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(1);
					mapper.setCcyCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(2);
					mapper.setCcyCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(3);
					mapper.setDivisionCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(4);
					mapper.setDivisionCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(5);
					mapper.setOrigDivisionCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(6);
					mapper.setOrigDivisionCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(7);
					mapper.setCustTypeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(8);
					mapper.setCustTypeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(9);
					mapper.setSubdivisionCodeIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(10);
					mapper.setSubdivisionCodeNotIn(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(11);
					mapper.setFixedLength(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(12);
					mapper.setMaturityDate(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(13);
					mapper.setBaseTenor(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(14);
					mapper.setMarginTenor(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(15);
					mapper.setApplicableCurve(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(16);
					mapper.setPrePost(mapperConversionService.getStringCellValue(currentCell));

					currentCell = currentRow.getCell(17);
					mapper.setFinalFtpCategory(mapperConversionService.getStringCellValue(currentCell));

					MapperVersion version = mapperVersionService.getMapper("PC");
					mapper.setVersion(version.getVersionChars() + (version.getCurrentVersion()+1));
					mapper.setUploadedDate(new Date());
					mapper.setUploadedBy(user.getName());
					mapper.setBankCode(appUser.getEntity());
					mappersList.add(mapper);

					numberOfRecords += 1;
					fileInfo.setNoOfRecords(numberOfRecords);
				}
				policyMapperTempRepository.save(mappersList);
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

		LOG.info(" End uploadToTemp ");
		return response;
	}

	@Override
	@Transactional
	public void clearRecords(FtpEntity ftpEntity) {
		LOG.info(" Start clearRecords ");
		policyMapperTempRepository.deleteInBulkByBankCode(ftpEntity);
		LOG.info(" End clearRecords ");
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain(FtpEntity ftpEntity) {
		LOG.info(" Start getDifferenceOfTempAndMain ");
		String bankCode = ftpEntity.getBankCode();
		List<String> tempNotInMain = policyMapperTempRepository.fetchRecordsNotInMain(bankCode);
		List<String> mainNotInTemp = policyMapperRepository.fetchRecordsNotInTemp(bankCode);
		Set<String> ftpCategorySet = new HashSet<>();
		ftpCategorySet.addAll(tempNotInMain);
		ftpCategorySet.addAll(mainNotInTemp);
		List<FTPPolicyMapperTemp> tempNotInMainList = policyMapperTempRepository
				.findByFtpCategoryInAndBankCodeOrderByFtpCategory(ftpCategorySet,ftpEntity);
		List<FTPPolicyMapper> mainNotInTempList = policyMapperRepository
				.findByFtpCategoryInAndBankCodeOrderByFtpCategory(ftpCategorySet,ftpEntity);
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
		Iterable<FTPPolicyMapper> mappersList = policyMapperRepository.findByBankCode(ftpEntity);
		List<FTPPolicyMapperArchive> archives = new ArrayList<>();
		mappersList.forEach(mapper -> {
			policyMapperRepository.delete(mapper);
			archives.add(mapperConversionService.mainToArchive(mapper));
		});
		if (!archives.isEmpty()) {
			policyMapperArchiveRepository.save(archives);
			LOG.info(" End getDifferenceOfTempAndMain ");
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
		Iterable<FTPPolicyMapperTemp> tempMappers = policyMapperTempRepository.findByBankCode(ftpEntity);
		List<FTPPolicyMapper> mainMappers = new ArrayList<>();
		MapperVersion version = mapperVersionService.getMapper("PC");

		Long nextMainVersion = version.getCurrentVersion() + 1;
		String mainVersion = version.getVersionChars() + nextMainVersion;
		tempMappers.forEach(mapper -> {
			policyMapperTempRepository.delete(mapper);
			mapper.setVersion(mainVersion);
			mainMappers.add(mapperConversionService.tempToMain(mapper));
		});
		if (!mainMappers.isEmpty()) {
			policyMapperRepository.save(mainMappers);
			mapperVersionService.updateMapperVersion("PC", nextMainVersion, version.getCurrentVersion());
			LOG.info(" End archive ");
			return (long) mainMappers.size();
		}
		LOG.info(" End insertToMain ");
		return 0L;
	}
}
