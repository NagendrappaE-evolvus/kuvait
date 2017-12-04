package com.evolvus.abk.ftp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.ItemMaster;

@Repository("itemRepository")
public interface ItemRepository extends CrudRepository<ItemMaster, Long> {
	
	List<ItemMaster> findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc(String itemFieldId,FtpEntity bankCode);

}
