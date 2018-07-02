package com.evolvus.abk.ftp.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.mappers.MapperVersion;
import com.evolvus.abk.ftp.repository.MapperVersionRepository;
import com.evolvus.abk.ftp.service.MapperVersionService;

@Service
public class MapperVersionServiceImpl implements MapperVersionService {
	
	@Autowired
	private MapperVersionRepository mapperVersionRepository;
	
	
	@Override
	@Transactional(readOnly=true)
	public MapperVersion getMapper(String name) {
		if(name!=null && !name.trim().equals(Constants.EMPTY)) {
			return mapperVersionRepository.findByMapperName(name);
		}
		return null;
	}

	@Override
	public MapperVersion updateMapperVersion(String name, Long mainVersion, Long archivalVersion) {
		MapperVersion mapperVersion = this.getMapper(name);
		if(mapperVersion!=null) {
			mapperVersion.setCurrArchVersion(archivalVersion);
			mapperVersion.setCurrentVersion(mainVersion);
			mapperVersionRepository.save(mapperVersion);
		}
		return mapperVersion;
	}

	@Override
	public Iterable<MapperVersion> listMappers() {
		return mapperVersionRepository.findAll();
	}
}
