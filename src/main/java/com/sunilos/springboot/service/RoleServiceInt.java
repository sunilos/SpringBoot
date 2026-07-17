package com.sunilos.springboot.service;

import java.util.List;

import com.sunilos.springboot.bean.Role;

/**
 * Role service interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public interface RoleServiceInt {

	public long add(Role dto);

	public void update(Role dto);

	public long save(Role dto);

	public Role delete(long id);

	public Role findById(long id);

	public Role findByName(String name);

	public List<Role> search();

	public List<Role> search(Role dto, int pageNo, int pageSize);
}
