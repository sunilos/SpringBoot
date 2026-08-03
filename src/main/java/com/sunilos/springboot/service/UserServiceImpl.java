package com.sunilos.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.User;
import com.sunilos.springboot.dao.UserDAOInt;

/**
 * User service class containing business logic.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceInt {

	@Autowired
	private UserDAOInt dao;

	@Override
	public UserDAOInt getDao() {
		return dao;
	}

	@Override
	public User findByLoginId(String loginId) {
		return dao.findByLoginId(loginId);
	}

	@Override
	public User findByEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public boolean changePassword(long id, String oldPassword, String newPassword) {
		User u = findById(id);
		if (u == null || !oldPassword.equals(u.getPassword())) {
			return false;
		}
		u.setPassword(newPassword);
		update(u);
		return true;
	}

}
