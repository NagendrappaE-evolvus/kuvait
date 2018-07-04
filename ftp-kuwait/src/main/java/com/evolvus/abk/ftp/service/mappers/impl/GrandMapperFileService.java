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
import org.apache.poi.ss.usermodel.CellType;
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
public class GrandMapperFileService<T,V,X> implements MapperFileService {

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
			Sheet datatypeSheet = workbook.getSheet("Mapping");
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			List<FTPGrandMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				// Clearing existing records
				clearRecords();
				while (rowIterator.hasNext()) {
					Row currentRow = rowIterator.next();
					FTPGrandMapperTemp mapper = null;
					mapper = new FTPGrandMapperTemp();
					if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
						mapper.setGlSubheadCode(currentRow.getCell(0).getStringCellValue().trim());
					} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setGlSubheadCode(String.valueOf(currentRow.getCell(0).getNumericCellValue()));
					}

					if (currentRow.getCell(1).getCellTypeEnum() == CellType.STRING) {
						mapper.setGlSubheadDesc(currentRow.getCell(1).getStringCellValue().trim());
					} else if (currentRow.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setGlSubheadDesc(String.valueOf(currentRow.getCell(1).getNumericCellValue()));
					}

					if (currentRow.getCell(2).getCellTypeEnum() == CellType.STRING) {
						mapper.setDrFtpCat(currentRow.getCell(2).getStringCellValue().trim());
					} else if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDrFtpCat(String.valueOf(currentRow.getCell(2).getNumericCellValue()));
					}

					if (currentRow.getCell(3).getCellTypeEnum() == CellType.STRING) {
						mapper.setCrFtpCat(currentRow.getCell(3).getStringCellValue().trim());
					} else if (currentRow.getCell(3).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCrFtpCat(String.valueOf(currentRow.getCell(3).getNumericCellValue()));
					}

					if (currentRow.getCell(4).getCellTypeEnum() == CellType.STRING) {
						mapper.setUserSubclassCodeIn(currentRow.getCell(4).getStringCellValue().trim());
					} else if (currentRow.getCell(4).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setUserSubclassCodeIn(String.valueOf(currentRow.getCell(4).getNumericCellValue()));
					}

					if (currentRow.getCell(5).getCellTypeEnum() == CellType.STRING) {
						mapper.setUserSubclassCodeNotIn(currentRow.getCell(5).getStringCellValue().trim());
					} else if (currentRow.getCell(5).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setUserSubclassCodeNotIn(String.valueOf(currentRow.getCell(5).getNumericCellValue()));
					}

					if (currentRow.getCell(6).getCellTypeEnum() == CellType.STRING) {
						mapper.setBAcidIn(currentRow.getCell(6).getStringCellValue().trim());
					} else if (currentRow.getCell(6).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setBAcidIn(String.valueOf(currentRow.getCell(6).getNumericCellValue()));
					}

					if (currentRow.getCell(7).getCellTypeEnum() == CellType.STRING) {
						mapper.setBAcidNotIn(currentRow.getCell(7).getStringCellValue().trim());
					} else if (currentRow.getCell(7).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setBAcidNotIn(String.valueOf(currentRow.getCell(7).getNumericCellValue()));
					}

					if (currentRow.getCell(8).getCellTypeEnum() == CellType.STRING) {
						mapper.setDivisionCodeIn(currentRow.getCell(8).getStringCellValue().trim());
					} else if (currentRow.getCell(8).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDivisionCodeIn(String.valueOf(currentRow.getCell(8).getNumericCellValue()));
					}

					if (currentRow.getCell(9).getCellTypeEnum() == CellType.STRING) {
						mapper.setDivisionCodeNotIn(currentRow.getCell(9).getStringCellValue().trim());
					} else if (currentRow.getCell(9).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDivisionCodeNotIn(String.valueOf(currentRow.getCell(9).getNumericCellValue()));
					}

					if (currentRow.getCell(10).getCellTypeEnum() == CellType.STRING) {
						mapper.setCustInLength(currentRow.getCell(10).getStringCellValue().trim());
					} else if (currentRow.getCell(10).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCustInLength(String.valueOf((int) currentRow.getCell(10).getNumericCellValue()));
					} else {
						currentRow.getCell(10).getCellTypeEnum();
					}

					if (currentRow.getCell(11).getCellTypeEnum() == CellType.STRING) {
						mapper.setCustTypeIn(currentRow.getCell(11).getStringCellValue().trim());
					} else if (currentRow.getCell(11).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCustTypeIn(String.valueOf(currentRow.getCell(11).getNumericCellValue()));
					}

					if (currentRow.getCell(12).getCellTypeEnum() == CellType.STRING) {
						mapper.setCustNotInLength(currentRow.getCell(12).getStringCellValue().trim());
					} else if (currentRow.getCell(12).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCustNotInLength(String.valueOf((int) currentRow.getCell(12).getNumericCellValue()));
					}

					if (currentRow.getCell(13).getCellTypeEnum() == CellType.STRING) {
						mapper.setCustTypeNotIn(currentRow.getCell(13).getStringCellValue().trim());
					} else if (currentRow.getCell(13).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCustTypeNotIn(String.valueOf(currentRow.getCell(13).getNumericCellValue()));
					}

					if (currentRow.getCell(14).getCellTypeEnum() == CellType.STRING) {
						mapper.setSubDivisionCodeIn(currentRow.getCell(14).getStringCellValue().trim());
					} else if (currentRow.getCell(14).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setSubDivisionCodeIn(String.valueOf(currentRow.getCell(14).getNumericCellValue()));
					}

					if (currentRow.getCell(15).getCellTypeEnum() == CellType.STRING) {
						mapper.setSubDivisionCodeNotIn(currentRow.getCell(15).getStringCellValue().trim());
					} else if (currentRow.getCell(15).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setSubDivisionCodeNotIn(String.valueOf(currentRow.getCell(15).getNumericCellValue()));
					}

					if (currentRow.getCell(16).getCellTypeEnum() == CellType.STRING) {
						mapper.setTradingBookNameIn(currentRow.getCell(16).getStringCellValue().trim());
					} else if (currentRow.getCell(16).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setTradingBookNameIn(String.valueOf(currentRow.getCell(16).getNumericCellValue()));
					}

					if (currentRow.getCell(17).getCellTypeEnum() == CellType.STRING) {
						mapper.setTradingBookNameNotIn(currentRow.getCell(17).getStringCellValue().trim());
					} else if (currentRow.getCell(17).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setTradingBookNameNotIn(String.valueOf(currentRow.getCell(17).getNumericCellValue()));
					}

					if (currentRow.getCell(18).getCellTypeEnum() == CellType.STRING) {
						mapper.setInstrumentClassIn(currentRow.getCell(18).getStringCellValue().trim());
					} else if (currentRow.getCell(18).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setInstrumentClassIn(String.valueOf(currentRow.getCell(18).getNumericCellValue()));
					}

					if (currentRow.getCell(19).getCellTypeEnum() == CellType.STRING) {
						mapper.setInstrumentClassNotIn(currentRow.getCell(19).getStringCellValue().trim());
					} else if (currentRow.getCell(19).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setInstrumentClassNotIn(String.valueOf(currentRow.getCell(19).getNumericCellValue()));
					}

					if (currentRow.getCell(20).getCellTypeEnum() == CellType.STRING) {
						mapper.setGroupByLogic(currentRow.getCell(20).getStringCellValue().trim());
					} else if (currentRow.getCell(20).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setGroupByLogic(String.valueOf(currentRow.getCell(20).getNumericCellValue()));
					}

					Double countValue = 0.0;
					if (currentRow.getCell(21).getCellTypeEnum() == CellType.NUMERIC) {
						countValue = currentRow.getCell(21).getNumericCellValue();
					}

					if (countValue != null) {
						mapper.setCount(countValue);
					}
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
			clearRecords();
		}

		audit.setMessage(response.getDescription());
		audit.setTxnStatus(response.getStatus());
		audit.setTxnEndTime(ftpAuditService.getCurrentTime());
		audit.setPostTxnVal(ftpAuditService.objectToJson(fileInfo));
		ftpAuditService.logAudit(audit);

		return response;
	}

	@Override
	public void clearRecords() {
		grandMapperTempRepository.deleteAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain() {
		List<String> tempNotInMain = grandMapperTempRepository.fetchRecordsNotInMain();
		List<String> mainNotInTemp = grandMapperMainRepository.fetchRecordsNotInTemp();
		Set<String> glSubheadSet = new HashSet<>();
		glSubheadSet.addAll(tempNotInMain);
		glSubheadSet.addAll(mainNotInTemp);
		List<FTPGrandMapperTemp> tempNotInMainList = grandMapperTempRepository
				.findByGlSubheadCodeInOrderByGlSubheadCode(glSubheadSet);
		List<FTPGrandMapper> mainNotInTempList = grandMapperMainRepository
				.findByGlSubheadCodeInOrderByGlSubheadCode(glSubheadSet);
		Map<String, List<? extends Object>> differences = new HashMap<>();
		differences.put(Constants.LIST_MAIN, mainNotInTempList);
		differences.put(Constants.LIST_TEMP, tempNotInMainList);
		return differences;
	}
	
	@Override
	@Transactional
	public Long archive() {
		Iterable<FTPGrandMapper> mappersList = grandMapperMainRepository.findAll();
		List<FTPGrandMapperArchive> archives = new ArrayList<>();
		mappersList.forEach(mapper-> {
			grandMapperMainRepository.delete(mapper);
			mapper.setId(null);
			archives.add(mapperConversionService.mainToArchive(mapper));
		});
		if(!archives.isEmpty()) {
			grandMapperArchiveRepository.save(archives);
			if(!archives.isEmpty()) {
				return (long) archives.size();
			}
		}
		return 0L;
	}
	
	@Override
	@Transactional
	public Long insertToMain() {
		Iterable<FTPGrandMapperTemp> tempMappers = grandMapperTempRepository.findAll();
		List<FTPGrandMapper> mainMappers = new ArrayList<>();
		MapperVersion version = mapperVersionService.getMapper("CT");
		
		Long nextMainVersion = version.getCurrentVersion() + 1;
		String mainVersion = version.getMapperKey()+nextMainVersion;
		tempMappers.forEach(mapper -> {
			grandMapperTempRepository.delete(mapper);
			mapper.setId(null);
			mapper.setVersion(mainVersion);
			mainMappers.add(mapperConversionService.tempToMain(mapper));
		});
		if(!mainMappers.isEmpty()) {
			grandMapperMainRepository.save(mainMappers);
			mapperVersionService.updateMapperVersion("CT", nextMainVersion, version.getCurrentVersion());
			return (long) mainMappers.size();
		}
		return 0L;
	}
}