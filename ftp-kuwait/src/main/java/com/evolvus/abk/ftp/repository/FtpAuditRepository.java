package com.evolvus.abk.ftp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpAudit;

@Repository("ftpAuditRepository")
public interface FtpAuditRepository extends CrudRepository<FtpAudit, Long> {
	
}
