package com.evolvus.abk.ftp.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;
import com.evolvus.abk.ftp.repository.mappers.temp.GrandMapperTempRepository;
import com.evolvus.abk.ftp.service.MapperFileService;

@Service
@Qualifier(value="GrandMapperFileService")
public class GrandMapperFileService  implements MapperFileService{

	@Autowired
	FtpAuditService ftpAuditService;
	
	@Autowired
	GrandMapperTempRepository grandMapperTempRepository;
	
	@Override
	public CustomResponse uploadToTemp(FileInfo fileInfo, String date, Principal user) {
		
		User appUser = ftpAuditService.getUserFromPrincipal(user);
		FileInputStream excelFile = null;
		Workbook workbook = null;
		try {
			excelFile = new FileInputStream(fileInfo.getUploadedFile());
			workbook = WorkbookFactory.create(excelFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet datatypeSheet = workbook.getSheet("Mapping");
		Iterator<Row> rowIterator = datatypeSheet.iterator();
		rowIterator.next();
		
		List<FTPGrandMapperTemp> tempList = grandMapperTempRepository.fetchRecordsNotInMain();
		if (rowIterator.hasNext()) {
			while (rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();
				FTPGrandMapperTemp mapper = new FTPGrandMapperTemp();
				
				mapper.setGlSubheadCode(currentRow.getCell(0).getStringCellValue().trim());
				mapper.setGlSubheadDesc(currentRow.getCell(1).getStringCellValue().trim());
				mapper.setDrFtpCat(currentRow.getCell(2).getStringCellValue().trim());
				mapper.setCrFtpCat(currentRow.getCell(3).getStringCellValue().trim());
				mapper.setUserSubclassCodeIn(currentRow.getCell(4).getStringCellValue().trim());
				mapper.setUserSubclassCodeNotIn(currentRow.getCell(5).getStringCellValue().trim());	
				mapper.setBAcidIn(currentRow.getCell(6).getStringCellValue().trim());
				mapper.setBAcidNotIn(currentRow.getCell(7).getStringCellValue().trim());
				mapper.setDivisionCodeIn(currentRow.getCell(8).getStringCellValue().trim());
				mapper.setDivisionCodeNotIn(currentRow.getCell(9).getStringCellValue().trim());
				mapper.setCustInLength(currentRow.getCell(10).getStringCellValue().trim());
				mapper.setCustTypeIn(currentRow.getCell(11).getStringCellValue().trim());
				mapper.setCustNotInLength(currentRow.getCell(12).getStringCellValue().trim());
				mapper.setCustTypeNotIn(currentRow.getCell(13).getStringCellValue().trim());
				mapper.setSubDivisionCodeIn(currentRow.getCell(14).getStringCellValue().trim());
				mapper.setSubDivisionCodeNotIn(currentRow.getCell(15).getStringCellValue().trim());
				mapper.setTradingBookNameIn(currentRow.getCell(16).getStringCellValue().trim());
				mapper.setTradingBookNameNotIn(currentRow.getCell(17).getStringCellValue().trim());
				mapper.setInstrumentClassIn(currentRow.getCell(18).getStringCellValue().trim());
				mapper.setInstrumentClassNotIn(currentRow.getCell(19).getStringCellValue().trim());
				mapper.setGroupByLogic(currentRow.getCell(20).getStringCellValue().trim());
				Double countValue = currentRow.getCell(21).getNumericCellValue();
				if(countValue!=null) {
					mapper.setCount(countValue);	
				}
				mapper.setUploadedDate(new Date());
				mapper.setUploadedBy(user.getName());
				mapper.setBankCode(appUser.getEntity());
				grandMapperTempRepository.save(mapper);
			}
		}
		return null;
	}

}
