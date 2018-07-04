package com.evolvus.abk.ftp.repository.mappers.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.mappers.main.FTPDivisionCodeMapper;

@Repository
public interface DivisionCodeMapperRepository extends CrudRepository<FTPDivisionCodeMapper, Long> {
	
}
