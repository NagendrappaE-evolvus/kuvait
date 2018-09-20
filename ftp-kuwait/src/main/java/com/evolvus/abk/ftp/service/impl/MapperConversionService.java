package com.evolvus.abk.ftp.service.impl;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.domain.mappers.archivals.FTPDivisionCodeMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPGrandMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPPolicyMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.archivals.FTPProductMapperArchive;
import com.evolvus.abk.ftp.domain.mappers.main.FTPDivisionCodeMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPGrandMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPPolicyMapper;
import com.evolvus.abk.ftp.domain.mappers.main.FTPProductMapper;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPDivisionCodeMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPPolicyMapperTemp;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPProductMapperTemp;

@Service
public class MapperConversionService {

	// Category Mapper Converters
	private static final Logger LOG = LoggerFactory.getLogger(MapperConversionService.class);
	DataFormatter dataFormatter = new DataFormatter();
	FormulaEvaluator evaluator = null;

	public FTPGrandMapper tempToMain(FTPGrandMapperTemp tempMapper) {
		LOG.info("Start tempToMain");
		FTPGrandMapper mainMapper = new FTPGrandMapper();

		mainMapper.setGlSubHeadCode(tempMapper.getGlSubHeadCode());
		mainMapper.setGlSubheadDesc(tempMapper.getGlSubheadDesc());

		mainMapper.setDrFtpCat(tempMapper.getDrFtpCat());
		mainMapper.setCrFtpCat(tempMapper.getCrFtpCat());
		
		mainMapper.setEntityNoIn(tempMapper.getEntityNoIn());
		mainMapper.setEntityNoNotIn(tempMapper.getEntityNoNotIn());

		mainMapper.setUserSubclassCodeIn(tempMapper.getUserSubclassCodeIn());
		mainMapper.setUserSubclassCodeNotIn(tempMapper.getUserSubclassCodeNotIn());

		mainMapper.setBacidIn(tempMapper.getBacidIn());
		mainMapper.setBacidNotIn(tempMapper.getBacidNotIn());

		mainMapper.setDivisionCodeIn(tempMapper.getDivisionCodeIn());
		mainMapper.setDivisionCodeNotIn(tempMapper.getDivisionCodeNotIn());

		mainMapper.setCustInLength(tempMapper.getCustInLength());
		mainMapper.setCustNotInLength(tempMapper.getCustNotInLength());

		mainMapper.setCustTypeIn(tempMapper.getCustTypeIn());
		mainMapper.setCustTypeNotIn(tempMapper.getCustTypeNotIn());

		mainMapper.setSubDivisionCodeIn(tempMapper.getSubDivisionCodeIn());
		mainMapper.setSubDivisionCodeNotIn(tempMapper.getSubDivisionCodeNotIn());

		mainMapper.setTradingBookNameIn(tempMapper.getTradingBookNameIn());
		mainMapper.setTradingBookNameNotIn(tempMapper.getTradingBookNameNotIn());

		mainMapper.setInstrumentClassIn(tempMapper.getInstrumentClassIn());
		mainMapper.setInstrumentClassNotIn(tempMapper.getInstrumentClassNotIn());

		mainMapper.setGroupByLogic(tempMapper.getGroupByLogic());
		mainMapper.setCount(tempMapper.getCount());

		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());

		mainMapper.setVersion(tempMapper.getVersion());
		mainMapper.setBankCode(tempMapper.getBankCode());

		LOG.info("End tempToMain");
		return mainMapper;
	}

	public FTPGrandMapperArchive mainToArchive(FTPGrandMapper mainMapper) {
		LOG.info("Start mainToArchive");
		FTPGrandMapperArchive archivalMapper = new FTPGrandMapperArchive();

		archivalMapper.setGlSubHeadCode(mainMapper.getGlSubHeadCode());
		archivalMapper.setGlSubheadDesc(mainMapper.getGlSubheadDesc());

		archivalMapper.setDrFtpCat(mainMapper.getDrFtpCat());
		archivalMapper.setCrFtpCat(mainMapper.getCrFtpCat());
		
		archivalMapper.setEntityNoIn(mainMapper.getEntityNoIn());
		archivalMapper.setEntityNoNotIn(mainMapper.getEntityNoNotIn());

		archivalMapper.setUserSubclassCodeIn(mainMapper.getUserSubclassCodeIn());
		archivalMapper.setUserSubclassCodeNotIn(mainMapper.getUserSubclassCodeNotIn());

		archivalMapper.setBacidIn(mainMapper.getBacidIn());
		archivalMapper.setBacidNotIn(mainMapper.getBacidNotIn());

		archivalMapper.setDivisionCodeIn(mainMapper.getDivisionCodeIn());
		archivalMapper.setDivisionCodeNotIn(mainMapper.getDivisionCodeNotIn());

		archivalMapper.setCustInLength(mainMapper.getCustInLength());
		archivalMapper.setCustNotInLength(mainMapper.getCustNotInLength());

		archivalMapper.setCustTypeIn(mainMapper.getCustTypeIn());
		archivalMapper.setCustTypeNotIn(mainMapper.getCustTypeNotIn());

		archivalMapper.setSubDivisionCodeIn(mainMapper.getSubDivisionCodeIn());
		archivalMapper.setSubDivisionCodeNotIn(mainMapper.getSubDivisionCodeNotIn());

		archivalMapper.setTradingBookNameIn(mainMapper.getTradingBookNameIn());
		archivalMapper.setTradingBookNameNotIn(mainMapper.getTradingBookNameNotIn());

		archivalMapper.setInstrumentClassIn(mainMapper.getInstrumentClassIn());
		archivalMapper.setInstrumentClassNotIn(mainMapper.getInstrumentClassNotIn());

		archivalMapper.setGroupByLogic(mainMapper.getGroupByLogic());
		archivalMapper.setCount(mainMapper.getCount());

		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());

		archivalMapper.setVersion(mainMapper.getVersion());
		archivalMapper.setBankCode(mainMapper.getBankCode());

		LOG.info("End mainToArchive");
		return archivalMapper;
	}

	// Product Mapper Converters

	public FTPProductMapper tempToMain(FTPProductMapperTemp tempMapper) {
		LOG.info("Start tempToMain");
		FTPProductMapper mainMapper = new FTPProductMapper();

		mainMapper.setFtpCategory(tempMapper.getFtpCategory());
		mainMapper.setProdCode(tempMapper.getProdCode());
		mainMapper.setProdDesc(tempMapper.getProdDesc());
		mainMapper.setAstLiabClas(tempMapper.getAstLiabClas());
		mainMapper.setCoreNonCore(tempMapper.getCoreNonCore());
		mainMapper.setCorePrnt(tempMapper.getCorePrnt());
		mainMapper.setBankCode(tempMapper.getBankCode());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());
		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setVersion(tempMapper.getVersion());

		LOG.info("End tempToMain");
		return mainMapper;

	}

	public FTPProductMapperArchive mainToArchive(FTPProductMapper mainMapper) {
		LOG.info("Start mainToArchive");
		FTPProductMapperArchive archivalMapper = new FTPProductMapperArchive();
		archivalMapper.setFtpCategory(mainMapper.getFtpCategory());
		archivalMapper.setProdCode(mainMapper.getProdCode());
		archivalMapper.setProdDesc(mainMapper.getProdDesc());
		archivalMapper.setAstLiabClas(mainMapper.getAstLiabClas());
		archivalMapper.setCoreNonCore(mainMapper.getCoreNonCore());
		archivalMapper.setCorePrnt(mainMapper.getCorePrnt());
		archivalMapper.setBankCode(mainMapper.getBankCode());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());
		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setVersion(mainMapper.getVersion());
		LOG.info("End mainToArchive");
		return archivalMapper;

	}

	// Policy Mapper Converter

	public FTPPolicyMapper tempToMain(FTPPolicyMapperTemp tempMapper) {
		LOG.info("Start tempToMain");
		FTPPolicyMapper mainMapper = new FTPPolicyMapper();

		mainMapper.setFtpCategory(tempMapper.getFtpCategory());
		mainMapper.setCcyCodeIn(tempMapper.getCcyCodeIn());
		mainMapper.setCcyCodeNotIn(tempMapper.getCcyCodeNotIn());

		mainMapper.setDivisionCodeIn(tempMapper.getDivisionCodeIn());
		mainMapper.setDivisionCodeNotIn(tempMapper.getDivisionCodeNotIn());

		mainMapper.setOrigDivisionCodeIn(tempMapper.getOrigDivisionCodeIn());
		mainMapper.setOrigDivisionCodeNotIn(tempMapper.getOrigDivisionCodeNotIn());

		mainMapper.setCustTypeIn(tempMapper.getCustTypeIn());
		mainMapper.setCustTypeNotIn(tempMapper.getCustTypeNotIn());

		mainMapper.setSubdivisionCodeIn(tempMapper.getSubdivisionCodeIn());
		mainMapper.setSubdivisionCodeNotIn(tempMapper.getSubdivisionCodeNotIn());

		mainMapper.setFixedLength(tempMapper.getFixedLength());
		mainMapper.setMaturityDate(tempMapper.getMaturityDate());

		mainMapper.setBaseTenor(tempMapper.getBaseTenor());
		mainMapper.setMarginTenor(tempMapper.getMarginTenor());

		mainMapper.setApplicableCurve(tempMapper.getApplicableCurve());
		mainMapper.setPrePost(tempMapper.getPrePost());

		mainMapper.setFinalFtpCategory(tempMapper.getFinalFtpCategory());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());

		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setBankCode(tempMapper.getBankCode());

		mainMapper.setVersion(tempMapper.getVersion());
		LOG.info("End tempToMain");
		return mainMapper;
	}

	public FTPPolicyMapperArchive mainToArchive(FTPPolicyMapper mainMapper) {

		LOG.info(" Start mainToArchive ");
		FTPPolicyMapperArchive archivalMapper = new FTPPolicyMapperArchive();
		archivalMapper.setFtpCategory(mainMapper.getFtpCategory());
		archivalMapper.setCcyCodeIn(mainMapper.getCcyCodeIn());
		archivalMapper.setCcyCodeNotIn(mainMapper.getCcyCodeNotIn());

		archivalMapper.setDivisionCodeIn(mainMapper.getDivisionCodeIn());
		archivalMapper.setDivisionCodeNotIn(mainMapper.getDivisionCodeNotIn());

		archivalMapper.setOrigDivisionCodeIn(mainMapper.getOrigDivisionCodeIn());
		archivalMapper.setOrigDivisionCodeNotIn(mainMapper.getOrigDivisionCodeNotIn());

		archivalMapper.setCustTypeIn(mainMapper.getCustTypeIn());
		archivalMapper.setCustTypeNotIn(mainMapper.getCustTypeNotIn());

		archivalMapper.setSubdivisionCodeIn(mainMapper.getSubdivisionCodeIn());
		archivalMapper.setSubdivisionCodeNotIn(mainMapper.getSubdivisionCodeNotIn());

		archivalMapper.setFixedLength(mainMapper.getFixedLength());
		archivalMapper.setMaturityDate(mainMapper.getMaturityDate());

		archivalMapper.setBaseTenor(mainMapper.getBaseTenor());
		archivalMapper.setMarginTenor(mainMapper.getMarginTenor());

		archivalMapper.setApplicableCurve(mainMapper.getApplicableCurve());
		archivalMapper.setPrePost(mainMapper.getPrePost());

		archivalMapper.setFinalFtpCategory(mainMapper.getFinalFtpCategory());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());

		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setBankCode(mainMapper.getBankCode());

		archivalMapper.setVersion(mainMapper.getVersion());
		LOG.info(" End mainToArchive ");
		return archivalMapper;
	}

	// Division Code Mappers
	public FTPDivisionCodeMapper tempToMain(FTPDivisionCodeMapperTemp tempMapper) {
		LOG.info(" Start tempToMain ");
		FTPDivisionCodeMapper mainMapper = new FTPDivisionCodeMapper();

		mainMapper.setGlSubHeadCode(tempMapper.getGlSubHeadCode());

		mainMapper.setGlshChar(tempMapper.getGlshChar());
		mainMapper.setEntityCode(tempMapper.getEntityCode());

		mainMapper.setCategory(tempMapper.getCategory());
		mainMapper.setDivisionDesc(tempMapper.getDivisionDesc());

		mainMapper.setOfficer(tempMapper.getOfficer());
		mainMapper.setSubDivisionIn(tempMapper.getSubDivisionIn());
		mainMapper.setSubDivisionNotIn(tempMapper.getSubDivisionNotIn());
		
		mainMapper.setDivision(tempMapper.getDivision());
		mainMapper.setFinalDivisionDesc(tempMapper.getFinalDivisionDesc());
		
		mainMapper.setFtpDivisionCode(tempMapper.getFtpDivisionCode());

		mainMapper.setUploadedBy(tempMapper.getUploadedBy());
		mainMapper.setUploadedDate(tempMapper.getUploadedDate());

		mainMapper.setBankCode(tempMapper.getBankCode());
		mainMapper.setVersion(tempMapper.getVersion());
		LOG.info(" End tempToMain ");
		return mainMapper;
	}

	public FTPDivisionCodeMapperArchive mainToArchive(FTPDivisionCodeMapper mainMapper) {
		LOG.info(" Start mainToArchive ");
		FTPDivisionCodeMapperArchive archivalMapper = new FTPDivisionCodeMapperArchive();

		archivalMapper.setGlSubHeadCode(mainMapper.getGlSubHeadCode());

		archivalMapper.setGlshChar(mainMapper.getGlshChar());
		archivalMapper.setEntityCode(mainMapper.getEntityCode());

		archivalMapper.setCategory(mainMapper.getCategory());
		archivalMapper.setDivisionDesc(mainMapper.getDivisionDesc());

		archivalMapper.setOfficer(mainMapper.getOfficer());
		archivalMapper.setSubDivisionIn(mainMapper.getSubDivisionIn());
		archivalMapper.setSubDivisionNotIn(mainMapper.getSubDivisionNotIn());
		
		archivalMapper.setDivision(mainMapper.getDivision());
		archivalMapper.setFinalDivisionDesc(mainMapper.getFinalDivisionDesc());

		archivalMapper.setFtpDivisionCode(mainMapper.getFtpDivisionCode());
		archivalMapper.setUploadedBy(mainMapper.getUploadedBy());
		archivalMapper.setUploadedDate(mainMapper.getUploadedDate());

		archivalMapper.setBankCode(mainMapper.getBankCode());
		archivalMapper.setVersion(mainMapper.getVersion());
		LOG.info(" End mainToArchive ");
		return archivalMapper;
	}

	public int getNumericCellValue(Cell currentCell, FormulaEvaluator evaluator) {
		/*currentCell = evaluator.evaluateInCell(currentCell);*/
		try {
			if (currentCell == null) {
				return -111;
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return (int) currentCell.getNumericCellValue();
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				return (int) currentCell.getNumericCellValue();
			}
			return Integer.parseInt(dataFormatter.formatCellValue(currentCell));

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return -111;
	}

	public Double getDoubleValueOfCurrentCell(Cell currentCell, FormulaEvaluator evaluator) {
		try {
			if (currentCell == null) {
				return null;
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return currentCell.getNumericCellValue();
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
				return Double.parseDouble(currentCell.getStringCellValue().trim());
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				return currentCell.getNumericCellValue();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BigDecimal getBigdecimalValueOfCurrentCell(Cell currentCell, FormulaEvaluator evaluator) {
		try {
			if (currentCell == null) {
				return null;
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return BigDecimal.valueOf(currentCell.getNumericCellValue());
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
				return BigDecimal.valueOf(Double.parseDouble(currentCell.getStringCellValue().trim()));
			} else if (currentCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				return BigDecimal.valueOf(currentCell.getNumericCellValue());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStringCellValue(Cell currentCell) {

		if (currentCell == null) {
			return "";
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return currentCell.getRichStringCellValue().toString().trim();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			String temp = dataFormatter.formatCellValue(currentCell).trim();
			if(temp.contains(","))
			{
				temp = ""+currentCell.getNumericCellValue();
			}
			return temp;
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return "";
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return dataFormatter.formatCellValue(currentCell).trim();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_ERROR) {
			return "";
		}
		return "";

	}
}
