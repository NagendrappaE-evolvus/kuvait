package com.evolvus.abk.ftp.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.FtpProduct;

@Repository("ftpProductRepository")
public interface FtpProductRepository extends CrudRepository<FtpProduct, Long>  {
	
	@Query("select prod.productCode as productCode,prod.productName as productName from FtpProduct prod where prod.bankCode=?1")
	List<Map<String,String>> findAllProductCodeAndProductNameByBankCode(FtpEntity bankCode);
	
	FtpProduct findByProductCodeAndBankCode(String productCode,FtpEntity bankCode);
}
