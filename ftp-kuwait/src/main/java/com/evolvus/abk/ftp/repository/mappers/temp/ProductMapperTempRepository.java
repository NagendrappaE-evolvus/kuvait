package com.evolvus.abk.ftp.repository.mappers.temp;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.evolvus.abk.ftp.domain.mappers.temp.FTPProductMapperTemp;

public interface ProductMapperTempRepository extends CrudRepository<FTPProductMapperTemp, Long>{

	@Query(value ="SELECT tempMapper.ftp_category FROM ftp_product_mapper_temp tempMapper where "
			+"CONCAT(tempMapper.ftp_category,tempMapper.prod_code,tempMapper.prod_desc,tempMapper.ast_liab_clas,"+
			"tempMapper.core_non_core,tempMapper.core_prnt) not in (SELECT CONCAT(mapper.ftp_category,mapper.prod_code,"+
			"mapper.prod_desc,mapper.ast_liab_clas,mapper.core_non_core,mapper.core_prnt) from ftp_product_mapper mapper)",
			nativeQuery=true)
	List<String> fetchRecordsNotInMain();

	List<FTPProductMapperTemp> findByFtpCategoryInOrderByFtpCategory(Set<String> ftpCategory);

}
