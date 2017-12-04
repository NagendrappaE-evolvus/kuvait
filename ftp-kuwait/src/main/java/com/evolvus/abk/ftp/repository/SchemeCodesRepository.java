package com.evolvus.abk.ftp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.KwtFtpSchemes;

@Repository("schemeCodesRepository")
public interface SchemeCodesRepository extends CrudRepository<KwtFtpSchemes, Long>{
	
	List<KwtFtpSchemes> findAllByBankCode(FtpEntity bankCode);
 	
	KwtFtpSchemes findBySchmCodeAndGlSubHeadCodeAndFtpCategory(String schmCode,String glSubHeadCode,String ftpCategory);
	KwtFtpSchemes findByschmDesc(String schmDesc);

	Page<KwtFtpSchemes> findByBankCodeAndSchmCodeContainingOrSchmDescContainingOrGlSubHeadCodeContainingOrFtpCategoryContaining(FtpEntity bankCode,String schmcode, String schmDesc,String glSubHeadCode, String ftpCategory,
			Pageable pageData);
}
