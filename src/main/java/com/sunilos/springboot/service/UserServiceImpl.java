package com.sunilos.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.User;
import com.sunilos.springboot.dao.UserRepositoryInt;

/**
 * User service class containing business logic.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Service
@Transactional
public class UserServiceImpl implements UserServiceInt {

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepositoryInt dao;

	@Override
	public long add(User dto) {
		User u = dao.save(dto);
		return u.getId();
	}

	@Override
	public void update(User dto) {
		dao.save(dto);
	}

	@Override
	public long save(User dto) {
		long id = dto.getId();
		if (id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}

	@Override
	public User delete(long id) {
		User u = findById(id);
		dao.deleteById(id);
		return u;
	}

	@Override
	public User findById(long id) {
		Optional<User> optional = dao.findById(id);
		return optional.orElse(null);
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
	public List<User> search() {
		return dao.findAll();
	}

	@Override
	public List<User> search(User dto, int pageNo, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
		Example<User> example = Example.of(dto);
		Page<User> page = dao.findAll(example, pageRequest);
		return page.getContent();
	}

	@Override
	public boolean changePassword(long id, String oldPassword, String newPassword) {
		User u = findById(id);
		if (u == null) return false;
		if (!oldPassword.equals(u.getPassword())) return false;
		u.setPassword(newPassword);
		dao.save(u);
		return true;
	}
}
