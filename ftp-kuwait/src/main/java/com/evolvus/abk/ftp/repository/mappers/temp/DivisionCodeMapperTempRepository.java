package com.evolvus.abk.ftp.repository.mappers.temp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.mappers.temp.FTPDivisionCodeMapperTemp;

@Repository
public interface DivisionCodeMapperTempRepository extends CrudRepository<FTPDivisionCodeMapperTemp, Long> {

}
