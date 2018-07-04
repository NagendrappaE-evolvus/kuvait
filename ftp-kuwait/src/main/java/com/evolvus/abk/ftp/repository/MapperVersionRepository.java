package com.evolvus.abk.ftp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.mappers.MapperVersion;

@Repository
public interface MapperVersionRepository extends CrudRepository<MapperVersion, Long> {
	MapperVersion findByMapperName(String mapperName);
	
	MapperVersion findByMapperKey(String mapperKey);
}
