package com.evolvus.abk.ftp.repository.mappers.main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.mappers.main.FTPPolicyMapper;

@Repository
public interface PolicyMapperRepository extends CrudRepository<FTPPolicyMapper,Long>{

}
