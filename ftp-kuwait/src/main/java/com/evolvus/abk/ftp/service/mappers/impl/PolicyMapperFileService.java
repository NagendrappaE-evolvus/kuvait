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
@Qualifier(value="PolicyMapperFileService")
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
	public CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user) {
		User appUser = ftpAuditService.getUserFromPrincipal(user);
		FileInputStream excelFile = null;
		Workbook workbook = null;
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
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();

			List<FTPPolicyMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				// Clearing existing records
				clearRecords(appUser.getEntity());
				rowIterator.next();
				while (rowIterator.hasNext()) {
					Row currentRow = rowIterator.next();
					FTPPolicyMapperTemp mapper = null;
					mapper = new FTPPolicyMapperTemp();
					if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
						mapper.setFtpCategory(currentRow.getCell(0).getStringCellValue().trim());
					} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setFtpCategory(String.valueOf(currentRow.getCell(0).getNumericCellValue()));
					}

					if (currentRow.getCell(1).getCellTypeEnum() == CellType.STRING) {
						mapper.setCcyCodeIn(currentRow.getCell(1).getStringCellValue().trim());
					} else if (currentRow.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCcyCodeIn(String.valueOf(currentRow.getCell(1).getNumericCellValue()));
					}

					if (currentRow.getCell(2).getCellTypeEnum() == CellType.STRING) {
						mapper.setCcyCodeNotIn(currentRow.getCell(2).getStringCellValue().trim());
					} else if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCcyCodeNotIn(String.valueOf(currentRow.getCell(2).getNumericCellValue()));
					}

					if (currentRow.getCell(3).getCellTypeEnum() == CellType.STRING) {
						mapper.setDivisionCodeIn(currentRow.getCell(3).getStringCellValue().trim());
					} else if (currentRow.getCell(3).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDivisionCodeIn(String.valueOf(currentRow.getCell(3).getNumericCellValue()));
					}

					if (currentRow.getCell(4).getCellTypeEnum() == CellType.STRING) {
						mapper.setDivisionCodeNotIn(currentRow.getCell(4).getStringCellValue().trim());
					} else if (currentRow.getCell(4).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDivisionCodeNotIn(String.valueOf(currentRow.getCell(4).getNumericCellValue()));
					}

					if (currentRow.getCell(5).getCellTypeEnum() == CellType.STRING) {
						mapper.setOrigDivisionCodeIn(currentRow.getCell(5).getStringCellValue().trim());
					} else if (currentRow.getCell(5).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setOrigDivisionCodeIn(String.valueOf(currentRow.getCell(5).getNumericCellValue()));
					}

					if (currentRow.getCell(6).getCellTypeEnum() == CellType.STRING) {
						mapper.setOrigDivisionCodeNotIn(currentRow.getCell(6).getStringCellValue().trim());
					} else if (currentRow.getCell(6).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setOrigDivisionCodeNotIn(String.valueOf(currentRow.getCell(6).getNumericCellValue()));
					}

					if (currentRow.getCell(7).getCellTypeEnum() == CellType.STRING) {
						mapper.setCustTypeIn(currentRow.getCell(7).getStringCellValue().trim());
					} else if (currentRow.getCell(7).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCustTypeIn(String.valueOf(currentRow.getCell(7).getNumericCellValue()));
					}

					if (currentRow.getCell(8).getCellTypeEnum() == CellType.STRING) {
						mapper.setCustTypeNotIn(currentRow.getCell(8).getStringCellValue().trim());
					} else if (currentRow.getCell(8).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCustTypeNotIn(String.valueOf(currentRow.getCell(8).getNumericCellValue()));
					}

					if (currentRow.getCell(9).getCellTypeEnum() == CellType.STRING) {
						mapper.setSubdivisionCodeIn(currentRow.getCell(9).getStringCellValue().trim());
					} else if (currentRow.getCell(9).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setSubdivisionCodeIn(String.valueOf(currentRow.getCell(9).getNumericCellValue()));
					}

					if (currentRow.getCell(10).getCellTypeEnum() == CellType.STRING) {
						mapper.setSubdivisionCodeNotIn(currentRow.getCell(10).getStringCellValue().trim());
					} else if (currentRow.getCell(10).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setSubdivisionCodeNotIn(String.valueOf((int) currentRow.getCell(10).getNumericCellValue()));
					}

					if (currentRow.getCell(11).getCellTypeEnum() == CellType.STRING) {
						mapper.setFixedLength(currentRow.getCell(11).getStringCellValue().trim());
					} else if (currentRow.getCell(11).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setFixedLength(String.valueOf(currentRow.getCell(11).getNumericCellValue()));
					}

					if (currentRow.getCell(12).getCellTypeEnum() == CellType.STRING) {
						mapper.setMaturityDate(currentRow.getCell(12).getStringCellValue().trim());
					} else if (currentRow.getCell(12).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setMaturityDate(String.valueOf((int) currentRow.getCell(12).getNumericCellValue()));
					}

					if (currentRow.getCell(13).getCellTypeEnum() == CellType.STRING) {
						mapper.setBaseTenor(currentRow.getCell(13).getStringCellValue().trim());
					} else if (currentRow.getCell(13).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setBaseTenor(String.valueOf(currentRow.getCell(13).getNumericCellValue()));
					}

					if (currentRow.getCell(14).getCellTypeEnum() == CellType.STRING) {
						mapper.setMarginTenor(currentRow.getCell(14).getStringCellValue().trim());
					} else if (currentRow.getCell(14).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setMarginTenor(String.valueOf(currentRow.getCell(14).getNumericCellValue()));
					}

					if (currentRow.getCell(15).getCellTypeEnum() == CellType.STRING) {
						mapper.setApplicableCurve(currentRow.getCell(15).getStringCellValue().trim());
					} else if (currentRow.getCell(15).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setApplicableCurve(String.valueOf(currentRow.getCell(15).getNumericCellValue()));
					}

					if (currentRow.getCell(16).getCellTypeEnum() == CellType.STRING) {
						mapper.setPrePost(currentRow.getCell(16).getStringCellValue().trim());
					} else if (currentRow.getCell(16).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setPrePost(String.valueOf(currentRow.getCell(16).getNumericCellValue()));
					}

					if (currentRow.getCell(17).getCellTypeEnum() == CellType.STRING) {
						mapper.setFinalFtpCategory(currentRow.getCell(17).getStringCellValue().trim());
					} else if (currentRow.getCell(17).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setFinalFtpCategory(String.valueOf(currentRow.getCell(17).getNumericCellValue()));
					}

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
		} catch (InvalidFormatException e) {
			response.setDescription("Invalid format of the document");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (IllegalStateException e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} catch (Exception e) {
			response.setDescription("Unable to parse data, please check the file format.");
			response.setStatus(Constants.STATUS_FAIL);
			audit.setStackTrace(ExceptionUtils.getStackTrace(e));
			LOG.error(response.getDescription() + " => " + audit.getStackTrace());
		} finally {
			if (workbook!=null) {
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

		return response;
	}

	@Override
	@Transactional
	public void clearRecords(FtpEntity ftpEntity) {
		policyMapperTempRepository.deleteInBulkByBankCode(ftpEntity);		
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain(FtpEntity ftpEntity) {
		String bankCode=ftpEntity.getBankCode();
		List<String> tempNotInMain = policyMapperTempRepository.fetchRecordsNotInMain(bankCode);
		List<String> mainNotInTemp = policyMapperRepository.fetchRecordsNotInTemp(bankCode);
		Set<String> ftpCategorySet = new HashSet<>();
		ftpCategorySet.addAll(tempNotInMain);
		ftpCategorySet.addAll(mainNotInTemp);
		List<FTPPolicyMapperTemp> tempNotInMainList = policyMapperTempRepository
				.findByFtpCategoryInOrderByFtpCategory(ftpCategorySet);
		List<FTPPolicyMapper> mainNotInTempList = policyMapperRepository
				.findByFtpCategoryInOrderByFtpCategory(ftpCategorySet);
		Map<String, List<? extends Object>> differences = new HashMap<>();
		differences.put(Constants.LIST_MAIN, mainNotInTempList);
		differences.put(Constants.LIST_TEMP, tempNotInMainList);
		return differences;
	}
	

	@Override
	@Transactional
	public Long archive(FtpEntity ftpEntity) {
		Iterable<FTPPolicyMapper> mappersList = policyMapperRepository.findByBankCode(ftpEntity);
		List<FTPPolicyMapperArchive> archives = new ArrayList<>();
		mappersList.forEach(mapper-> {
			policyMapperRepository.delete(mapper);
			archives.add(mapperConversionService.mainToArchive(mapper));
		});
		if(!archives.isEmpty()) {
			policyMapperArchiveRepository.save(archives);
			if(!archives.isEmpty()) {
				return (long) archives.size();
			}
		}
		return 0L;
	}

	@Override
	@Transactional
	public Long insertToMain(FtpEntity ftpEntity) {
		Iterable<FTPPolicyMapperTemp> tempMappers = policyMapperTempRepository.findByBankCode(ftpEntity);
		List<FTPPolicyMapper> mainMappers = new ArrayList<>();
		MapperVersion version = mapperVersionService.getMapper("PC");
		
		Long nextMainVersion = version.getCurrentVersion() + 1;
		String mainVersion = version.getVersionChars()+nextMainVersion;
		tempMappers.forEach(mapper -> {
			policyMapperTempRepository.delete(mapper);
			mapper.setVersion(mainVersion);
			mainMappers.add(mapperConversionService.tempToMain(mapper));
		});
		if(!mainMappers.isEmpty()) {
			policyMapperRepository.save(mainMappers);
			mapperVersionService.updateMapperVersion("PC", nextMainVersion, version.getCurrentVersion());
			return (long) mainMappers.size();
		}
		return 0L;
	}

}
