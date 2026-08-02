package com.sunilos.springboot.dao;

import com.sunilos.springboot.bean.User;

/**
 * User DAO interface. Defines basic CRUD operations backed directly by
 * an EntityManager implementation.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

public interface UserDAOInt extends BaseDAOInt<User> {
	/**
	 * Finds a user by login ID
	 *
	 * @param loginId
	 * @return
	 */
	public User findByLoginId(String loginId);

	/**
	 * Finds a user by email
	 *
	 * @param email
	 * @return
	 */
	public User findByEmail(String email);
}
