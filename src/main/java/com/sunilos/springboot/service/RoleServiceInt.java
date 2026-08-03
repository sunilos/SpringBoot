package com.sunilos.springboot.service;

import com.sunilos.springboot.bean.Role;

/**
 * Role service interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public interface RoleServiceInt extends BaseServiceInt<Role> {

	public Role findByName(String name);
}
