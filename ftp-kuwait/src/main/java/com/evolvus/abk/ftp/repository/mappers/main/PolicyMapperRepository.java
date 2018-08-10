package com.evolvus.abk.ftp.repository.mappers.main;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.mappers.main.FTPPolicyMapper;

@Repository
public interface PolicyMapperRepository extends CrudRepository<FTPPolicyMapper, Long> {

	@Query(value = "SELECT mapper.ftp_category FROM ftp_curve_mapper mapper where mapper.bank_id=:bankCode and CONCAT(mapper.ftp_category,mapper.ccy_code_in,mapper.ccy_code_not_in,mapper.division_code_in,mapper.division_code_not_in,"
			+ "mapper.orig_division_code_in,mapper.orig_division_code_not_in,mapper.cust_type_in,mapper.cust_type_not_in,mapper.subdivision_code_in,"
			+ "mapper.subdivision_code_not_in,mapper.fixed_float,mapper.maturity_date,mapper.base_tenor,mapper.margin_tenor,mapper.applicable_curve,mapper.pre_post,mapper.final_ftp_category) not in (SELECT CONCAT(tempMapper.ftp_category,tempMapper.ccy_code_in,tempMapper.ccy_code_not_in,tempMapper.division_code_in,tempMapper.division_code_not_in,"
			+ "tempMapper.orig_division_code_in,tempMapper.orig_division_code_not_in,tempMapper.cust_type_in,tempMapper.cust_type_not_in,tempMapper.subdivision_code_in,"
			+ "tempMapper.subdivision_code_not_in,tempMapper.fixed_float,tempMapper.maturity_date,tempMapper.base_tenor,tempMapper.margin_tenor,tempMapper.applicable_curve,tempMapper.pre_post,tempMapper.final_ftp_category) FROM ftp_curve_mapper_temp tempMapper where tempMapper.bank_id=:bankCode)", nativeQuery = true)
	List<String> fetchRecordsNotInTemp(@Param("bankCode")String bankCode);

	List<FTPPolicyMapper> findByFtpCategoryInAndBankCodeOrderByFtpCategory(Set<String> ftpCategories,FtpEntity ftpEntity);

	Iterable<FTPPolicyMapper> findByBankCode(FtpEntity ftpEntity);
}
