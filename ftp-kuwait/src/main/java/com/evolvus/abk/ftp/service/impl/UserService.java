package com.evolvus.abk.ftp.service.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.evolvus.abk.ftp.bean.CustomResponse;
import com.evolvus.abk.ftp.constants.Constants;
import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.User;
import com.evolvus.abk.ftp.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	FtpAuditService ftpAuditService;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	public User findByUsername(String username, FtpEntity entity) {
		LOG.debug("Start findByUsername");
		User user = null;
		try {
			user = userRepository.findByUsernameAndEntity(username, entity);
		} catch (Exception e) {
			LOG.error("Error in fetching user.");
		}
		LOG.debug("End findByUsername");
		return user;
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public CustomResponse saveUser(User user, User currentUser) {
		LOG.debug("Start :: saveUser");
		CustomResponse response = new CustomResponse();
		try {
			if(currentUser.getIsAdmin()==null || !currentUser.getIsAdmin()) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Only admins have permission to create users.");
				return response;
			} 
			User existingUser = findByUsername(user.getUsername(), user.getEntity());
			if (existingUser == null) {
				user.setCreatedBy(currentUser.getUsername());
				user.setCreatedDate(ftpAuditService.getCurrentTime());
				userRepository.save(user);
				response.setStatus(Constants.STATUS_OK);
				response.setDescription("User created successfully.");
			} else {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("User already exist.");
			}
		} catch (Exception e) {
			response.setStatus(Constants.STATUS_OK);
			response.setDescription("Error in saving user.");
			LOG.error("Error in saveUser");
		}
		LOG.debug("End: saveUser");
		return response;
	}

	public CustomResponse updateUser(User user, User currentUser) {
		LOG.debug("Start :: updateUser");
		CustomResponse response = new CustomResponse();
		User existingUser = null;
		try {
			if(currentUser.getIsAdmin()==null || !currentUser.getIsAdmin()) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("Only admins have permission to update user details.");
				return response;
			} 
			existingUser = findByUsername(user.getUsername(), user.getEntity());
			if ( null  != existingUser && !user.getUserId().equals(existingUser.getUserId())) {
				response.setStatus(Constants.STATUS_FAIL);
				response.setDescription("A user with same username exist in target region.");
			} else {
				if (existingUser != null) {
					if(currentUser.getUsername().equalsIgnoreCase(user.getUsername())) {
						response.setStatus(Constants.STATUS_FAIL);
						response.setDescription("Self update is not allowed.");
						return response;
					}
					user.setLastUpdatedBy(currentUser.getUsername());
					user.setLastUpdatedDate(ftpAuditService.getCurrentTime());
					userRepository.save(user);
					response.setStatus(Constants.STATUS_OK);
					response.setDescription("User updated successfully.");
				} else {
					response.setStatus(Constants.STATUS_FAIL);
					response.setDescription("User does not exist.");
				}
			}
		} catch (Exception e) {
			response.setStatus(Constants.STATUS_FAIL);
			response.setDescription("Error in updating user");
			LOG.error("Error in updateUser.");
		}
		LOG.debug("End: updateUser");
		return response;
	}

	public Pageable createPageRequest(Integer currentPage, Integer pageSize) {
		return (new PageRequest(currentPage, pageSize, new Sort("username")));
	}

	public Page<User> getPagedUsers(String username, Pageable pageData, FtpEntity entity) {
		Page<User> pagedUsers = null;
		LOG.debug("Start getPagedUsers");
		try {
			pagedUsers = userRepository.findByEntityAndUsernameContaining(entity, username, pageData);
		} catch (Exception e) {
			LOG.error("Error in paging users =>" + ExceptionUtils.getStackTrace(e));
		}
		LOG.debug("End getPagedUsers");
		return pagedUsers;
	}

}
