package com.evolvus.abk.ftp.repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.MarginCurveExtd;

@Repository("mrgnCurveExtdRepository")
public interface MrgnCurveExtdRepository extends CrudRepository<MarginCurveExtd, Long>{
	Long countByRateDateInFileAndBankCode(Date rateDateInFile,FtpEntity bankCode);
	@Transactional
	Long deleteInBulkByRateDateInFileAndBankCode(Date rateDateInFile,FtpEntity bankCode);
}
