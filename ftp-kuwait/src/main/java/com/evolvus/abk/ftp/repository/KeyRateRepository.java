package com.evolvus.abk.ftp.repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.rates.KeyRates;

public interface KeyRateRepository extends CrudRepository<KeyRates, Long>{

	Long countByBusinessCloseDateAndBankCode(Date applyDate, FtpEntity entity);

	@Transactional
	Long deleteInBulkByBusinessCloseDateAndBankCode(Date applyDate, FtpEntity entity);

}
