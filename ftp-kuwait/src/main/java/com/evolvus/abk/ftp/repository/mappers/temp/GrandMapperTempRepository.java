package com.evolvus.abk.ftp.repository.mappers.temp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;

public interface GrandMapperTempRepository extends CrudRepository<FTPGrandMapperTemp, Long> {
	
	@Query(value ="SELECT * FROM ftp_grand_mapper_temp tmpMapper where "
			+ "CONCAT(tmpMapper.gl_subhead_code,tmpMapper.gl_subhead_desc) not in (SELECT CONCAT(mapper.gl_subhead_code,mapper.gl_subhead_desc) from ftp_grand_mapper mapper)",
			nativeQuery=true)
	List<FTPGrandMapperTemp> fetchRecordsNotInMain();
}
