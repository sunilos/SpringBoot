package com.sunilos.springboot.dao;

import com.sunilos.springboot.bean.Role;

/**
 * Role DAO interface. Defines basic CRUD operations backed directly by
 * an EntityManager implementation.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

public interface RoleDAOInt extends BaseDAOInt<Role> {
	/**
	 * Finds a role by name
	 *
	 * @param name
	 * @return
	 */
	public Role findByName(String name);
}
