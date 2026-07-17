package com.sunilos.springboot.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class Criteria<T> implements Specification<T> {

	private String key;
	private String operation;
	private Object value;

	public Criteria(String key, String operation, Object value) {
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (operation.equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(root.<String>get(key), value.toString());
		} else if (operation.equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(root.<String>get(key), value.toString());
		} else if (operation.equalsIgnoreCase("=")) {
			if (root.get(key).getJavaType() == String.class) {
				return builder.like(root.<String>get(key), "%" + value + "%");
			} else {
				return builder.equal(root.get(key), value);
			}
		}
		return null;
	}

}
