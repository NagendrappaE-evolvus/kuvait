package com.evolvus.abk.ftp.controller;

import java.io.File;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.evolvus.abk.ftp.bean.CustomException;
import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.bean.FileInfo;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.service.RateService;
import com.evolvus.abk.ftp.service.MapperFileService;
import com.evolvus.abk.ftp.service.impl.FileUploadService;
import com.evolvus.abk.ftp.service.impl.FtpAuditService;

@RestController
@RequestMapping("file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Value("${upload.file.location}")
    String uploadFolder;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    FtpAuditService ftpAuditService;
    
    @Autowired(required=true)
    @Qualifier(value="GrandMapperFileService")
    MapperFileService grandMapperService;
    
    @Autowired(required=true)
    @Qualifier(value="CurrencyRateFileService")
    RateService currencyRateService;
    
    @Autowired(required=true)
    @Qualifier(value="KeyRateFileService")
    RateService keyRateService;

    /**
     * File UPLOAD.
     *
     * @param multipartfile
     *            the multipartfile
     * @param userName
     *            the user name
     * @return the response entity
     * @throws InterruptedException
     *             the interrupted exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> fileUpload(@RequestParam("file") MultipartFile multipartfile,
            @RequestParam("fileType") String fileType, @RequestParam("date") String date,
            @RequestParam("overwrite") Boolean overwrite, Principal user) throws InterruptedException {
        LOG.debug("Start fileUpload");
        HttpStatus httpStatus = HttpStatus.OK;
        CustomResponse customResponse = null;

        try {
            FileInfo fileInfo = this.saveUploadedFile(multipartfile);
            if (fileInfo.getFileSaved()) {
                if ("CT".equals(fileType)) {
                    customResponse = grandMapperService.uploadToTemp(fileInfo, date, user);
                    if(customResponse.getStatus().equals(Constants.STATUS_OK)) {
                    	customResponse.setData(grandMapperService.getDifferenceOfTempAndMain()); 
                    }
                } else if ("All Key Rates".equals(fileType)) {
                    customResponse = fileUploadService.uploadKeyRates(fileType, fileInfo, date, overwrite,
                            ftpAuditService.getUserFromPrincipal(user));
                } else if ("Margin Adjustment".equals(fileType)) {
                    customResponse = fileUploadService.uploadMarginAdjustmentRates(fileType, fileInfo, date,
                            overwrite, ftpAuditService.getUserFromPrincipal(user));
                } else if ("Margin Curve Extended".equals(fileType)) {
                    customResponse = fileUploadService.uploadMarginCurveExtendedRates(fileType, fileInfo, date,
                            overwrite, ftpAuditService.getUserFromPrincipal(user));
                }else if ("Currency Rates".equals(fileType)) {
               	 if(overwrite) {
               		 fileUploadService.deleteExistingRecords(fileType, date, ftpAuditService.getUserFromPrincipal(user));
               	 }
                	 customResponse = currencyRateService.uploadRates(fileType, fileInfo, date, ftpAuditService.getUserFromPrincipal(user), overwrite);
                }else if ("Static Rates".equals(fileType)) {
                  	 if(overwrite) {
                   		 fileUploadService.deleteExistingRecords(fileType, date, ftpAuditService.getUserFromPrincipal(user));
                   	 }
                	customResponse = keyRateService.uploadRates(fileType, fileInfo, date, ftpAuditService.getUserFromPrincipal(user), overwrite);
                } else {
                    throw new CustomException("Invalid file type.");
                }
            } else {
                throw new CustomException("Error in writing file to disk.");
            }
        } catch (CustomException e) {
            customResponse = new CustomResponse();
            customResponse.setStatus(Constants.STATUS_OK);
            customResponse.setDescription(e.getMessage());
            LOG.error(e.getMessage());
        } catch (Exception e) {
            customResponse = new CustomResponse();
            customResponse.setStatus(Constants.STATUS_OK);
            customResponse.setDescription("Error in file upload.");
            LOG.error("Error in data for file upload.");
        }
        LOG.debug("End fileUpload");
        return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
    }

    @RequestMapping(value = "/checkDataAvailable", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> checkDataAvailable(@RequestParam("fileType") String fileType,
            @RequestParam("date") String date, Principal user) {
        HttpStatus httpStatus = HttpStatus.OK;
        CustomResponse customResponse = null;
        LOG.debug("Start checkDataAvailable");
        try {
            customResponse = fileUploadService.dataExistingCount(fileType, date,
                    ftpAuditService.getUserFromPrincipal(user));
        } catch (Exception e) {
            customResponse = new CustomResponse();
            customResponse.setStatus(Constants.STATUS_FAIL);
            customResponse.setDescription("Error in checking data.");
            LOG.error("Error in finding count.");
        }
        LOG.debug("End checkDataAvailable");
        return new ResponseEntity<CustomResponse>(customResponse, httpStatus);
    }

    private FileInfo saveUploadedFile(MultipartFile multipartfile) {
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
        File sourceFile = null;
        LOG.debug("Start saveUploadedFile");
        FileInfo fileInfo = new FileInfo();
        try {
            String fileName = multipartfile.getOriginalFilename();
            if(fileName.contains(File.separator)) {
                fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1,fileName.length());
            }
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_"
					+ timeStamp.format(new Timestamp(System.currentTimeMillis()))
					+ fileName.substring(fileName.lastIndexOf("."), fileName.length());

			File sourceDirectory = new File(uploadFolder+File.separator+new SimpleDateFormat("ddMMyyyy").format(new Date()));
			if (!sourceDirectory.exists()) {
				sourceDirectory.mkdirs();
			}
			sourceFile = new File(sourceDirectory, fileName);

			sourceFile.createNewFile();
			
            multipartfile.transferTo(sourceFile);
            fileInfo.setFileName(sourceFile.getName());
            fileInfo.setFilePath(sourceFile.getAbsolutePath());
            fileInfo.setFileSize(String.valueOf(sourceFile.length() / 1024) + " kb");
            fileInfo.setUploadedFile(sourceFile);
            fileInfo.setFileSaved(Boolean.TRUE);

        } catch (Exception e) {
            fileInfo.setStackTrace(ExceptionUtils.getStackTrace(e));
            fileInfo.setFileSaved(Boolean.FALSE);
            LOG.error("Exception in saveUploadedFile: {} " + fileInfo.getStackTrace());
        } 
        fileInfo.setUploadDate(new Date());
        LOG.debug("End saveUploadedFile");
        return fileInfo;
    }
}
