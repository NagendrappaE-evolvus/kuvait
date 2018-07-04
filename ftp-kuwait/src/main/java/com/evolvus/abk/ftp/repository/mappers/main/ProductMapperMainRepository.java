package com.evolvus.abk.ftp.repository.mappers.main;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.evolvus.abk.ftp.domain.mappers.main.FTPProductMapper;

public interface ProductMapperMainRepository extends CrudRepository<FTPProductMapper, Long>{

	@Query(value ="SELECT mapper.ftp_category FROM ftp_product_mapper mapper where "
			+ "CONCAT(mapper.ftp_category,mapper.prod_code,"+ 
			"mapper.prod_desc,mapper.ast_liab_clas,mapper.core_non_core,mapper.core_prnt) not in (SELECT "+
			"CONCAT(tempMapper.ftp_category,tempMapper.prod_code,tempMapper.prod_desc,tempMapper.ast_liab_clas,"+ 
			"tempMapper.core_non_core,tempMapper.core_prnt) from ftp_product_mapper_temp tempMapper)",
			nativeQuery=true)
	List<String> fetchRecordsNotInTemp();

	List<FTPProductMapper> findByFtpCategoryInOrderByFtpCategory(Set<String> ftpCategory);

}
