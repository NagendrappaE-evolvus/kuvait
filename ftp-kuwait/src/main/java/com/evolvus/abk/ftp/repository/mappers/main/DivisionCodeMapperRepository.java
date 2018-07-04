package com.evolvus.abk.ftp.repository.mappers.main;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.mappers.main.FTPDivisionCodeMapper;

@Repository
public interface DivisionCodeMapperRepository extends CrudRepository<FTPDivisionCodeMapper, Long> {
	
	@Query(value ="SELECT mapper.gl_sub_head_code FROM ftp_product_mapper mapper where "
			+ "CONCAT(mapper.gl_sub_head_code,mapper.glsh_char ,mapper.entity_code ,mapper.category,"+ 
			"mapper.division_desc,mapper.officer,mapper.subdivision,mapper.division,"+
			"mapper.final_division_desc) not in (SELECT "+
			"CONCAT(tempMapper.gl_sub_head_code,tempMapper.glsh_char ,tempMapper.entity_code ,tempMapper.category,"+
			"tempMapper.division_desc,tempMapper.officer,tempMapper.subdivision,tempMapper.division,"+
			"tempMapper.final_division_desc) from ftp_product_mapper_temp tempMapper)",
			nativeQuery=true)
	List<String> fetchRecordsNotInTemp();
}
