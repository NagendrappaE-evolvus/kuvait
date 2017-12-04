package com.evolvus.abk.ftp.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.domain.ItemMaster;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(ItemService.class);
	
	public List<ItemMaster> findItemsByFieldId(String fieldId,User user) {
		List<ItemMaster> itemList = Collections.emptyList();
		LOG.debug("Start : findItemsByFieldId");
		try {
			itemList = itemRepository.findAllByItemFieldIdAndBankCodeOrderByDisplayOrderAsc(fieldId,user.getEntity());
		} catch(NullPointerException e) {
			LOG.error("Error in fetching items =>"+ExceptionUtils.getStackTrace(e));
		} catch (Exception e) {
			LOG.error("Error in fetching items =>"+ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End : findItemsByFieldId");
		return itemList;
	}
}
