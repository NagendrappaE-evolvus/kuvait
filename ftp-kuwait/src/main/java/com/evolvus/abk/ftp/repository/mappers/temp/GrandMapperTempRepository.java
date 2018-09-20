package com.evolvus.abk.ftp.repository.mappers.temp;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.mappers.temp.FTPGrandMapperTemp;

public interface GrandMapperTempRepository extends CrudRepository<FTPGrandMapperTemp, Long> {

	@Query(value = "SELECT tmpMapper.gl_sub_head_code FROM ftp_grand_mapper_temp tmpMapper where tmpMapper.bank_id=:bankCode and "
			+ "CONCAT(tmpMapper.gl_sub_head_code,tmpMapper.gl_sub_head_desc,tmpMapper.dr_ftp_category,tmpMapper.entity_no_in,tmpMapper.entity_no_not_in,tmpMapper.cr_ftp_category,tmpMapper.user_subclass_code_in,tmpMapper.user_subclass_code_not_in,"
			+ "tmpMapper.bacid_in,tmpMapper.bacid_not_in,tmpMapper.division_code_in,tmpMapper.division_code_not_in,tmpMapper.cust_in_length,tmpMapper.cust_type_in,tmpMapper.cust_notin_length,"
			+ "tmpMapper.cust_type_not_in,tmpMapper.subdivision_code_in,tmpMapper.subdivision_code_not_in,tmpMapper.trading_book_name_in,tmpMapper.trading_book_name_not_in,tmpMapper.instrument_sub_class_in,"
			+ "tmpMapper.instrument_sub_class_not_in,tmpMapper.group_by_logic,tmpMapper.count) not in (SELECT CONCAT(mapper.gl_sub_head_code,mapper.gl_sub_head_desc,mapper.dr_ftp_category,mapper.entity_no_in,mapper.entity_no_not_in,mapper.cr_ftp_category,mapper.user_subclass_code_in,mapper.user_subclass_code_not_in,"
			+ "mapper.bacid_in,mapper.bacid_not_in,mapper.division_code_in,mapper.division_code_not_in,mapper.cust_in_length,mapper.cust_type_in,mapper.cust_notin_length,"
			+ "mapper.cust_type_not_in,mapper.subdivision_code_in,mapper.subdivision_code_not_in,mapper.trading_book_name_in,mapper.trading_book_name_not_in,mapper.instrument_sub_class_in,"
			+ "mapper.instrument_sub_class_not_in,mapper.group_by_logic,mapper.count) from ftp_grand_mapper mapper where mapper.bank_id=:bankCode)", nativeQuery = true)
	List<String> fetchRecordsNotInMain(@Param("bankCode") String bankCode);

	List<FTPGrandMapperTemp> findByGlSubHeadCodeInAndBankCodeOrderByGlSubHeadCode(Set<String> glSubheadCode,
			FtpEntity entity);

	@Transactional
	Long deleteInBulkByBankCode(FtpEntity entity);

	Iterable<FTPGrandMapperTemp> findByBankCode(FtpEntity ftpEntity);
	
	@Query(value ="SELECT  tmpMapper.gl_sub_head_code,tmpMapper.gl_sub_head_desc,tmpMapper.dr_ftp_category,tmpMapper.entity_no_in,tmpMapper.entity_no_not_in,tmpMapper.cr_ftp_category,tmpMapper.user_subclass_code_in,tmpMapper.user_subclass_code_not_in," + 
			"tmpMapper.bacid_in,tmpMapper.bacid_not_in,tmpMapper.division_code_in,tmpMapper.division_code_not_in,tmpMapper.cust_in_length,tmpMapper.cust_type_in,tmpMapper.cust_notin_length," + 
			"tmpMapper.cust_type_not_in,tmpMapper.subdivision_code_in,tmpMapper.subdivision_code_not_in,tmpMapper.trading_book_name_in,tmpMapper.trading_book_name_not_in,tmpMapper.instrument_sub_class_in," + 
			"tmpMapper.instrument_sub_class_not_in,tmpMapper.group_by_logic,tmpMapper.count,tmpMapper.uploaded_by ,null as uploaded_date,tmpMapper.version,tmpMapper.bank_id,max(tmpMapper.g_id) as g_id from ftp_grand_mapper_temp tmpMapper " + 
			"inner join ftp_grand_mapper_temp tmpMap2 on tmpMapper.g_id=tmpMap2.g_id " + 
			"group by tmpMapper.gl_sub_head_code,tmpMapper.gl_sub_head_desc,tmpMapper.dr_ftp_category,tmpMapper.entity_no_in,tmpMapper.entity_no_not_in,tmpMapper.cr_ftp_category,tmpMapper.user_subclass_code_in,tmpMapper.user_subclass_code_not_in," + 
			"tmpMapper.bacid_in,tmpMapper.bacid_not_in,tmpMapper.division_code_in,tmpMapper.division_code_not_in,tmpMapper.cust_in_length,tmpMapper.cust_type_in,tmpMapper.cust_notin_length," + 
			"tmpMapper.cust_type_not_in,tmpMapper.subdivision_code_in,tmpMapper.subdivision_code_not_in,tmpMapper.trading_book_name_in,tmpMapper.trading_book_name_not_in,tmpMapper.instrument_sub_class_in," + 
			"tmpMapper.instrument_sub_class_not_in,tmpMapper.group_by_logic,tmpMapper.count,tmpMapper.uploaded_by,tmpMapper.version,tmpMapper.bank_id having count(*)>1",
			nativeQuery=true)
	List<FTPGrandMapperTemp> findDuplicatesInTemp();
	
	
}
