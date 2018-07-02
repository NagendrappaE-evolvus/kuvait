package com.evolvus.abk.ftp.service;


import com.evolvus.abk.ftp.domain.mappers.MapperVersion;

public interface MapperVersionService {
	
	MapperVersion getMapper(String name);
	
	MapperVersion updateMapperVersion(String name, Long mainVersion, Long archivalVersion);
	
	Iterable<MapperVersion> listMappers();
	
}
