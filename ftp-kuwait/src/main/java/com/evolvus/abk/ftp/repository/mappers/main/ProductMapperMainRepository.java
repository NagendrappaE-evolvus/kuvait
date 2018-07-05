package com.evolvus.abk.ftp.repository.mappers.main;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.mappers.main.FTPProductMapper;

public interface ProductMapperMainRepository extends CrudRepository<FTPProductMapper, Long>{

	@Query(value ="SELECT mapper.ftp_category FROM ftp_product_mapper mapper where mapper.bank_code=:bankCode and "
			+ "CONCAT(mapper.ftp_category,mapper.prod_code,"+ 
			"mapper.prod_desc,mapper.ast_liab_clas,mapper.core_non_core,mapper.core_prnt) not in (SELECT "+
			"CONCAT(tempMapper.ftp_category,tempMapper.prod_code,tempMapper.prod_desc,tempMapper.ast_liab_clas,"+ 
			"tempMapper.core_non_core,tempMapper.core_prnt) from ftp_product_mapper_temp tempMapper where tempMapper.bank_code=:bankCode)",
			nativeQuery=true)
	List<String> fetchRecordsNotInTemp(@Param("bankCode")String bankCode);

	List<FTPProductMapper> findByFtpCategoryAndBankCodeInOrderByFtpCategory(Set<String> ftpCategory,FtpEntity ftpEntity);

	Iterable<FTPProductMapper> findByBankCode(FtpEntity ftpEntity);

	void deleteInBulkByBankCode(FtpEntity ftpEntity);

}
