package com.evolvus.abk.ftp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;

@Repository("ftpEntityRepository")
public interface FtpEntityRepository extends CrudRepository<FtpEntity, Long> {
	FtpEntity findByBankCodeAndIsActive(String bankCode,Boolean isActive);
	List<FtpEntity> findAllByIsActive(Boolean isActive);
}
