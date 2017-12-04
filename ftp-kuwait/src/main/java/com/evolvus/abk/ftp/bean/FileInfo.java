package com.evolvus.abk.ftp.bean;

import java.io.File;
import java.util.Date;

import com.evolvus.abk.ftp.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FileInfo {

	public FileInfo() {
		fileSize = filePath = dataFrequency = fileName = Constants.EMPTY;
		uploadedFile = null;
		uploadDate = rateApplicableDate = null;
		noOfRecords = 0L;
	}

	private String fileName;

	private Long noOfRecords;

	private String filePath;

	private Date uploadDate;

	private String fileSize;
	
	private Boolean fileSaved;
	
	@JsonIgnore
	private String stackTrace;

	@JsonIgnore
	private File uploadedFile;

	private Date rateApplicableDate;

	private String dataFrequency;

	@Override
	public String toString() {
		return String.format(
				"[FileInfo={fileName=%s,noOfRecords=%d,filePath=%s,uploadDate=%s,fileSize=%s,rateApplicableDate=%s,dataFrequency=%s}]",
				fileName, noOfRecords, filePath, uploadDate, fileSize, rateApplicableDate, dataFrequency);
	}
}
