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
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPProductMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPProductMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPProductMapperTemp;
import com.evolvus.abk.ftp.repository.mappers.main.ProductMapperMainRepository;
import com.evolvus.abk.ftp.repository.mappers.main.archivals.ProductMapperArchiveRepository;
import com.evolvus.abk.ftp.repository.mappers.temp.ProductMapperTempRepository;
import com.evolvus.abk.ftp.service.MapperFileService;
import com.evolvus.abk.ftp.service.MapperVersionService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;
import com.evolvus.abk.ftp.service.impl.MapperConversionService;

@Service
@Qualifier(value = "ProductMapperFileService")
public class ProductMapperFileService implements MapperFileService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductMapperFileService.class);

	@Autowired
	FtpAuditService ftpAuditService;
	
	@Autowired
	ProductMapperTempRepository productMapperTempRepository;

	@Autowired
	ProductMapperMainRepository productMapperMainRepository;
	
	@Autowired
	ProductMapperArchiveRepository productMapperArchiveRepository;
	
	@Autowired
	MapperConversionService mapperConversionService;
	
	@Autowired
	MapperVersionService mapperVersionService;
	
	@Override
	public CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user) {
		
		User appUser = ftpAuditService.getUserFromPrincipal(user);
		FileInputStream excelFile = null;
		Workbook workbook = null;
		CustomResponse response = new CustomResponse();
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(FTPProductMapperTemp.class.getName());
		audit.setBankCode(appUser.getEntity());
		audit.setTxnUser(appUser.getUsername());

		try {
			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			List<FTPProductMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				clearRecords(appUser.getEntity());
				while (rowIterator.hasNext()) {
					Row currentRow = rowIterator.next();
					FTPProductMapperTemp mapper = null;
					mapper = new FTPProductMapperTemp();
					if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
						mapper.setFtpCategory(currentRow.getCell(0).getStringCellValue().trim());
					} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setFtpCategory((String.valueOf(currentRow.getCell(0).getNumericCellValue())));
					}

					if (currentRow.getCell(1).getCellTypeEnum() == CellType.STRING) {
						mapper.setProdCode(currentRow.getCell(1).getStringCellValue().trim());
					} else if (currentRow.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setProdCode((String.valueOf(currentRow.getCell(1).getNumericCellValue())));
					}
					
					if (currentRow.getCell(2).getCellTypeEnum() == CellType.STRING) {
						mapper.setProdDesc(currentRow.getCell(2).getStringCellValue().trim());
					} else if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setProdDesc((String.valueOf(currentRow.getCell(2).getNumericCellValue())));
					}
			
					if (currentRow.getCell(3).getCellTypeEnum() == CellType.STRING) {
						mapper.setAstLiabClas(currentRow.getCell(3).getStringCellValue().trim());
					} else if (currentRow.getCell(3).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setAstLiabClas((String.valueOf(currentRow.getCell(3).getNumericCellValue())));
					}
					if (currentRow.getCell(4).getCellTypeEnum() == CellType.STRING) {
						mapper.setCoreNonCore(currentRow.getCell(4).getStringCellValue().trim());
					} else if (currentRow.getCell(4).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCoreNonCore((String.valueOf(currentRow.getCell(4).getNumericCellValue())));
					}
					if (currentRow.getCell(5).getCellTypeEnum() == CellType.STRING) {
						mapper.setCorePrnt(currentRow.getCell(5).getStringCellValue().trim());
					} else if (currentRow.getCell(5).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCorePrnt((String.valueOf(currentRow.getCell(5).getNumericCellValue())));
					}
	
					mapper.setUploadedDate(new Date());
					mapper.setUploadedBy(user.getName());
					mapper.setBankCode(appUser.getEntity());
					mappersList.add(mapper);

					numberOfRecords += 1;
					fileInfo.setNoOfRecords(numberOfRecords);
				}
				productMapperTempRepository.save(mappersList);
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

		return response;
	}

	@Override
	@Transactional
	public void clearRecords(FtpEntity ftpEntity) {
		productMapperTempRepository.deleteInBulkByBankCode(ftpEntity);		
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain(FtpEntity ftpEntity) {
		
		String bankCode = ftpEntity.getBankCode();
		List<String> tempNotInMain = productMapperTempRepository.fetchRecordsNotInMain(bankCode);
		List<String> mainNotInTemp = productMapperMainRepository.fetchRecordsNotInTemp(bankCode);
		Set<String> ftpCategory = new HashSet<>();
		ftpCategory.addAll(tempNotInMain);
		ftpCategory.addAll(mainNotInTemp);
		List<FTPProductMapperTemp> tempNotInMainList = productMapperTempRepository
				.findByFtpCategoryInAndBankCodeOrderByFtpCategory(ftpCategory,ftpEntity);
		List<FTPProductMapper> mainNotInTempList = productMapperMainRepository
				.findByFtpCategoryAndBankCodeInOrderByFtpCategory(ftpCategory,ftpEntity);
		Map<String, List<? extends Object>> differences = new HashMap<>();
		differences.put(Constants.LIST_MAIN, mainNotInTempList);
		differences.put(Constants.LIST_TEMP, tempNotInMainList);
		return differences;
	}

	@Override
	@Transactional
	public Long archive(FtpEntity ftpEntity) {
		Iterable<FTPProductMapper> mappersList = productMapperMainRepository.findByBankCode(ftpEntity);
		List<FTPProductMapperArchive> archives = new ArrayList<>();
		mappersList.forEach(mapper-> {
			//productMapperMainRepository.delete(mapper);
			mapper.setId(null);
			archives.add(mapperConversionService.mainToArchive(mapper));
		});
		productMapperMainRepository.deleteInBulkByBankCode(ftpEntity);
		if(!archives.isEmpty()) {
			productMapperArchiveRepository.save(archives);
			if(!archives.isEmpty()) {
				return (long) archives.size();
			}
		}
		return 0L;
	}

	@Override
	@Transactional
	public Long insertToMain(FtpEntity ftpEntity) {
		Iterable<FTPProductMapperTemp> tempMappers = productMapperTempRepository.findByBankCode(ftpEntity);
		List<FTPProductMapper> mainMappers = new ArrayList<>();
		MapperVersion version = mapperVersionService.getMapper("PD");
		
		Long nextMainVersion = version.getCurrentVersion() + 1;
		String mainVersion = version.getVersionChars()+nextMainVersion;
		tempMappers.forEach(mapper -> {
			//productMapperTempRepository.delete(mapper);
			mapper.setId(null);
			mapper.setVersion(mainVersion);
			mainMappers.add(mapperConversionService.tempToMain(mapper));
		});
		clearRecords(ftpEntity);
		if(!mainMappers.isEmpty()) {
			productMapperMainRepository.save(mainMappers);
			mapperVersionService.updateMapperVersion("PD", nextMainVersion, version.getCurrentVersion());
			return (long) mainMappers.size();
		}
		return 0L;
	}

}
