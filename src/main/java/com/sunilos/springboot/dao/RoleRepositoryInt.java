package com.sunilos.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.Role;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Repository
public interface RoleRepositoryInt extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

	/**
	 * Finds a role by name.
	 */
	public Role findByName(String name);
}
