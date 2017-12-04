package com.evolvus.abk.ftp.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evolvus.abk.ftp.domain.FtpRuleConfiguration;
import com.evolvus.abk.ftp.domain.ParameterConfiguration;

@Repository("paramRepository")
public interface ParamRepository extends CrudRepository<ParameterConfiguration, Long>{

	@Transactional
	Long deleteByRuleConfig(FtpRuleConfiguration rule);
	
}
