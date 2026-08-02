package com.sunilos.springboot.dao;

import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.User;

import jakarta.persistence.NoResultException;

/**
 * User DAO implementation. Performs CRUD operations directly through
 * the JPA EntityManager instead of a Spring Data repository.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

@Repository
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAOInt {

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public User findByLoginId(String loginId) {
		try {
			return entityManager.createQuery("from User where loginId = :loginId", User.class)
					.setParameter("loginId", loginId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByEmail(String email) {
		try {
			return entityManager.createQuery("from User where email = :email", User.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
