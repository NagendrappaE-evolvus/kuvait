package com.evolvus.abk.ftp.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.RuleExpressions;
import com.evolvus.abk.ftp.domain.RuleMappings;
@Repository("ruleExpressionsRepository")
public interface RuleExpressionsRepository extends CrudRepository<RuleExpressions, Long>{
	
	@Transactional
	Long deleteByRuleMap(RuleMappings ruleMap);
}
