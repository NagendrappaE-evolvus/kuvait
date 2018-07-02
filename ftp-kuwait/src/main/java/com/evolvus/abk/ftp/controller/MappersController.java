package com.evolvus.abk.ftp.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.service.MapperFileService;

@RestController
@RequestMapping("mappers")
public class MappersController {
	
    private static final Logger LOG = LoggerFactory.getLogger(MappersController.class);
    

    @Autowired(required=true)
    @Qualifier(value="GrandMapperFileService")
    private MapperFileService grandMapperService;
    
	/**
     * Process Mappers.
     *
     * param1 mapperName
     */
    @RequestMapping(value = "/process-mappers", method = RequestMethod.POST)
    public ResponseEntity<CustomResponse> processMapper(@RequestParam("mapperName") String mapperName, Principal user) {
        LOG.debug("Start process mapper");
        HttpStatus httpStatus = HttpStatus.OK;
        CustomResponse customResponse = new CustomResponse();
        try {
        	if ("Grand Mapper".equals(mapperName)) {
        			grandMapperService.archive();
        			Long mapperRecords = grandMapperService.insertToMain();
        			if(mapperRecords > 0L) {
        				customResponse.setData("{mapperRecords:"+mapperRecords+"}");
        				customResponse.setStatus("OK");
        				customResponse.setDescription("Data saved successfully");
        			}
        	}
        } catch(Exception e) {

        }
        return new ResponseEntity<>(customResponse, httpStatus);    
    }
}
