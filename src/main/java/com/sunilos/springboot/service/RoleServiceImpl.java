package com.sunilos.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.Role;
import com.sunilos.springboot.dao.RoleDAOInt;

/**
 * Role service class containing business logic.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleServiceInt {

	@Autowired
	private RoleDAOInt dao;

	@Override
	public RoleDAOInt getDao() {
		return dao;
	}

	@Override
	public Role findByName(String name) {
		return dao.findByName(name);
	}

}
