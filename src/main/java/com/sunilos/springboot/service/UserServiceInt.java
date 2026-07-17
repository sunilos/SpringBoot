package com.sunilos.springboot.service;

import java.util.List;

import com.sunilos.springboot.bean.User;

/**
 * User service interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public interface UserServiceInt {

	public long add(User dto);

	public void update(User dto);

	public long save(User dto);

	public User delete(long id);

	public User findById(long id);

	public User findByLoginId(String loginId);

	public User findByEmail(String email);

	public List<User> search();

	public List<User> search(User dto, int pageNo, int pageSize);

	public boolean changePassword(long id, String oldPassword, String newPassword);
}
