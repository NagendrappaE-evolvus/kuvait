package com.evolvus.abk.ftp.repository.mappers.main.archivals;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.mappers.archivals.FTPDivisionCodeMapperArchive;

@Repository
public interface DivisionCodeMapperArchiveRepository extends CrudRepository<FTPDivisionCodeMapperArchive, Long> {
	
}
