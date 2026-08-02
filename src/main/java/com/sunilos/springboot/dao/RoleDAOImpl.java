package com.sunilos.springboot.dao;

import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.Role;

import jakarta.persistence.NoResultException;

/**
 * Role DAO implementation. Performs CRUD operations directly through
 * the JPA EntityManager instead of a Spring Data repository.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

@Repository
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAOInt {

	@Override
	public Class<Role> getEntityClass() {
		return Role.class;
	}

	@Override
	public Role findByName(String name) {
		try {
			return entityManager.createQuery("from Role where name = :name", Role.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
