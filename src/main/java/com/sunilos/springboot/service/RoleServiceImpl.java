package com.sunilos.springboot.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RoleServiceImpl implements RoleServiceInt {

	private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDAOInt dao;

	@Override
	public long add(Role dto) {
		return dao.add(dto);
	}

	@Override
	public void update(Role dto) {
		dao.update(dto);
	}

	@Override
	public long save(Role dto) {
		long id = dto.getId();
		if (id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}

	@Override
	public Role delete(long id) {
		Role r = findById(id);
		dao.delete(id);
		return r;
	}

	@Override
	public Role findById(long id) {
		return dao.findById(id);
	}

	@Override
	public Role findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<Role> search() {
		return dao.findAll();
	}

	@Override
	public List<Role> search(Role dto, int pageNo, int pageSize) {
		Map<String, Object> params = new LinkedHashMap<>();
		if (dto.getName() != null) {
			params.put("name", dto.getName());
		}
		if (dto.getDescription() != null) {
			params.put("description", dto.getDescription());
		}
		return dao.findAll(params, pageNo - 1, pageSize);
	}
}
