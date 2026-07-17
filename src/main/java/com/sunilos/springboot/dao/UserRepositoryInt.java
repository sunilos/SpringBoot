package com.sunilos.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.User;

/**
 * User Data Access Object (DAO) interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Repository
public interface UserRepositoryInt extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	/**
	 * Finds a user by login ID.
	 */
	public User findByLoginId(String loginId);

	/**
	 * Finds a user by email.
	 */
	public User findByEmail(String email);
}
