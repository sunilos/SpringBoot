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

import com.sunilos.springboot.bean.Role;
import com.sunilos.springboot.dao.RoleRepositoryInt;

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
	private RoleRepositoryInt dao;

	@Override
	public long add(Role dto) {
		Role r = dao.save(dto);
		return r.getId();
	}

	@Override
	public void update(Role dto) {
		dao.save(dto);
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
		dao.deleteById(id);
		return r;
	}

	@Override
	public Role findById(long id) {
		Optional<Role> optional = dao.findById(id);
		return optional.orElse(null);
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
		PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
		Example<Role> example = Example.of(dto);
		Page<Role> page = dao.findAll(example, pageRequest);
		return page.getContent();
	}
}
