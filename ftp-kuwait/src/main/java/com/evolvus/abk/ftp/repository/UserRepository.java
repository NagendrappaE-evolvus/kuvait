package com.evolvus.abk.ftp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evolvus.abk.ftp.domain.FtpEntity;
import com.evolvus.abk.ftp.domain.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsernameAndEntity(String username,FtpEntity entity);
	
	Page<User> findByEntityAndUsernameContaining(FtpEntity entity,String username,Pageable page);
}
