package com.evolvus.abk.ftp.repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.CurrencyCurveRates;
import com.evolvus.abk.ftp.domain.FtpEntity;


@Repository("ccyCurveRateRepository")
public interface CcyCurveRateRepository extends CrudRepository<CurrencyCurveRates, Long> {
	Long countByRateDateInFileAndBankCode(Date rateDateInFile,FtpEntity bankCode);
	
	@Transactional
	Long deleteInBulkByRateDateInFileAndBankCode(Date rateDateInFile,FtpEntity bankCode);
}
