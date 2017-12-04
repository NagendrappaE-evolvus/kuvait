package com.evolvus.abk.ftp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.RuleMappings;

@Repository("ruleMappingRepository")
public interface RuleMappingRepository extends CrudRepository<RuleMappings, Long>{
	
	@Query("select distinct ruleTarget from RuleMappings where targetType = ?1 and product=?2")
	List<String> findDistinctRuleTargetByTargetTypeAndProduct(String targetType,FtpProduct product);
	
	List<RuleMappings> findByTargetTypeAndProduct(String targetType,FtpProduct product);
	
	List<RuleMappings> findByTargetTypeAndRuleTargetAndProduct(String targetType,String ruleTarget,FtpProduct product);
	
}
