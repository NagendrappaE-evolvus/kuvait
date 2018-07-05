package com.evolvus.abk.ftp.repository.mappers.temp;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPDivisionCodeMapperTemp;

@Repository
public interface DivisionCodeMapperTempRepository extends CrudRepository<FTPDivisionCodeMapperTemp, Long> {

	@Query(value ="SELECT tempMapper.gl_sub_head_code FROM FTP_FINAL_DIVISION_MAPPER_TEMP tempMapper where tempMapper.bank_code=:bankCode and "
			+"CONCAT(tempMapper.gl_sub_head_code,tempMapper.glsh_char ,tempMapper.entity_code ,tempMapper.category," + 
			"tempMapper.division_desc,tempMapper.officer,tempMapper.subdivision,tempMapper.division," + 
			"tempMapper.final_division_desc) not in (SELECT CONCAT(mapper.gl_sub_head_code,mapper.glsh_char ,mapper.entity_code ,mapper.category, " + 
			"mapper.division_desc,mapper.officer,mapper.subdivision,mapper.division," + 
			"mapper.final_division_desc) from FTP_FINAL_DIVISION_MAPPER mapper where mapper.bank_code=:bankCode)",
			nativeQuery=true)
	List<String> fetchRecordsNotInMain(@Param("bankCode")String bankCode);
	
	List<FTPDivisionCodeMapperTemp> findByGlSubHeadCodeInAndBankCodeOrderByGlSubHeadCode(Set<String> glSubHeadCode,FtpEntity ftpEntity);

	@Transactional
	Long deleteInBulkByBankCode(FtpEntity ftpEntity);

	Iterable<FTPDivisionCodeMapperTemp> findByBankCode(FtpEntity ftpEntity);
}
