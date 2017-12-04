package com.evolvus.abk.ftp.repository;


import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.FtpProduct;
import com.evolvus.abk.ftp.domain.FtpRuleConfiguration;

@Repository("ftpRuleConfigRepository")
public interface FtpRuleConfigRepository extends CrudRepository<FtpRuleConfiguration, Long> {

	Page<FtpRuleConfiguration> findByBankCodeAndProductAndRuleNameContaining(FtpEntity bankCode,FtpProduct product, String searchString, Pageable pageable);

	Long countByRuleName(String ruleName);
}
