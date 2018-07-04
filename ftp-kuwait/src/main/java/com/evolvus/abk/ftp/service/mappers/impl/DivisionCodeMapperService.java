package com.evolvus.abk.ftp.service.mappers.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpAudit;
import com.evolvus.abk.ftp.domain.User;
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
public class DivisionCodeMapperService implements MapperFileService {

	private static final Logger LOG = LoggerFactory.getLogger(DivisionCodeMapperService.class);

	@Autowired
	FtpAuditService ftpAuditService;
	
	@Autowired
	MapperConversionService mapperConversionService;
	
	@Autowired
	MapperVersionService mapperVersionService;
	
	@Autowired
	DivisionCodeMapperTempRepository divisionMapperTempRepository;

	@Autowired
	DivisionCodeMapperRepository divisiontMapperMainRepository;
	
	@Autowired
	DivisionCodeMapperArchiveRepository divisionMapperArchiveRepository;
	
	@Override
	public CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user) {
		
		User appUser = ftpAuditService.getUserFromPrincipal(user);
		FileInputStream excelFile = null;
		Workbook workbook = null;
		CustomResponse response = new CustomResponse();
		FtpAudit audit = new FtpAudit();
		audit.setTxnStartTime(ftpAuditService.getCurrentTime());
		audit.setTxnAction(Constants.TXN_FILE_UPLOAD);
		audit.setTxnObjectType(FTPDivisionCodeMapperTemp.class.getName());
		audit.setBankCode(appUser.getEntity());
		audit.setTxnUser(appUser.getUsername());
		
		try {
			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
			Sheet datatypeSheet = workbook.getSheet("Mapping");
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			rowIterator.next();

			List<FTPDivisionCodeMapperTemp> mappersList = new ArrayList<>();
			Long numberOfRecords = 0L;
			if (rowIterator.hasNext()) {
				clearRecords();
				while (rowIterator.hasNext()) {
					Row currentRow = rowIterator.next();
					FTPDivisionCodeMapperTemp mapper = null;
					mapper = new FTPDivisionCodeMapperTemp();
					if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
						mapper.setGlsubHeadCode(currentRow.getCell(0).getStringCellValue().trim());
					} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setGlsubHeadCode((String.valueOf(currentRow.getCell(0).getNumericCellValue())));
					}

					 if (currentRow.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setGlshChar((int) currentRow.getCell(1).getNumericCellValue());
					}
					
					if (currentRow.getCell(2).getCellTypeEnum() == CellType.STRING) {
						mapper.setEntityCode(currentRow.getCell(2).getStringCellValue().trim());
					} else if (currentRow.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setEntityCode((String.valueOf(currentRow.getCell(2).getNumericCellValue())));
					}
			
					if (currentRow.getCell(3).getCellTypeEnum() == CellType.STRING) {
						mapper.setCategory(currentRow.getCell(3).getStringCellValue().trim());
					} else if (currentRow.getCell(3).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setCategory((String.valueOf(currentRow.getCell(3).getNumericCellValue())));
					}
					if (currentRow.getCell(4).getCellTypeEnum() == CellType.STRING) {
						mapper.setDivisionDesc(currentRow.getCell(4).getStringCellValue().trim());
					} else if (currentRow.getCell(4).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDivisionDesc((String.valueOf(currentRow.getCell(4).getNumericCellValue())));
					}
					if (currentRow.getCell(5).getCellTypeEnum() == CellType.STRING) {
						mapper.setOfficer(currentRow.getCell(5).getStringCellValue().trim());
					} else if (currentRow.getCell(5).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setOfficer((String.valueOf(currentRow.getCell(5).getNumericCellValue())));
					}
					
					if (currentRow.getCell(6).getCellTypeEnum() == CellType.STRING) {
						mapper.setSubDivision(currentRow.getCell(6).getStringCellValue().trim());
					} else if (currentRow.getCell(6).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setSubDivision((String.valueOf(currentRow.getCell(6).getNumericCellValue())));
					}
					
					if (currentRow.getCell(7).getCellTypeEnum() == CellType.STRING) {
						mapper.setDivision(currentRow.getCell(7).getStringCellValue().trim());
					} else if (currentRow.getCell(7).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setDivision((String.valueOf(currentRow.getCell(7).getNumericCellValue())));
					}
					
					if (currentRow.getCell(8).getCellTypeEnum() == CellType.STRING) {
						mapper.setFinalDivisionDesc(currentRow.getCell(8).getStringCellValue().trim());
					} else if (currentRow.getCell(8).getCellTypeEnum() == CellType.NUMERIC) {
						mapper.setFinalDivisionDesc((String.valueOf(currentRow.getCell(8).getNumericCellValue())));
					}
	
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
		
	}

	@Override
	public Map<String, List<? extends Object>> getDifferenceOfTempAndMain() {

		return null;
	}

	@Override
	public Long archive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long insertToMain() {
		// TODO Auto-generated method stub
		return null;
	}

}
