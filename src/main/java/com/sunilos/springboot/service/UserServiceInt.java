package com.sunilos.springboot.service;

import com.sunilos.springboot.bean.User;

/**
 * User service interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public interface UserServiceInt extends BaseServiceInt<User> {

	public User findByLoginId(String loginId);

	public User findByEmail(String email);

	public boolean changePassword(long id, String oldPassword, String newPassword);
}
