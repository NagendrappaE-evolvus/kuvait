package com.evolvus.abk.ftp.repository;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.rates.CurrencyRates;

@Repository("currencyRateRepository")
public interface CurrencyRateRepository extends CrudRepository<CurrencyRates, Long>{

	Long countByBusinessCloseDateAndBankCode(Date applyDate, FtpEntity entity);

	Long deleteInBulkByBusinessCloseDateAndBankCode(Date applyDate, FtpEntity entity);

    
}
