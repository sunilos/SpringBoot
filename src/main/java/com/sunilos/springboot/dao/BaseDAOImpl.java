package com.sunilos.springboot.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sunilos.springboot.bean.BaseDTO;
import com.sunilos.springboot.common.ApplicationException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Marksheet DAO implementation. Performs CRUD operations directly through
 * the JPA EntityManager instead of a Spring Data repository.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

@Repository
public abstract class BaseDAOImpl<D extends BaseDTO> implements BaseDAOInt<D> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional
	public long add(D dto) {
		entityManager.persist(dto);
		return dto.getId();
	}

	@Override
	@Transactional
	public void update(D dto) {
		entityManager.merge(dto);
	}

	@Override
	@Transactional
	public void delete(long id) {
		D m = findById(id);
		if (m != null) {
			entityManager.remove(m);
		}
	}

	@Override
	public D findById(long id) {
		return entityManager.find(getEntityClass(), id);
	}

	public boolean exists(long id) {
		Long count = entityManager.createQuery(
				"select count(m) from " + getEntityClassName() + " m where m.id = :id", Long.class)
				.setParameter("id", id)
				.getSingleResult();
		return count > 0;
	}

	@Override
	public List<D> findAll() {
		return entityManager.createQuery("from " + getEntityClassName(), getEntityClass()).getResultList();
	}

	@Override
	public List<D> findAll(Map<String, Object> params) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<D> query = cb.createQuery(getEntityClass());
		Root<D> root = query.from(getEntityClass());

		List<Predicate> predicates = new ArrayList<>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			predicates.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
		}

		query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<D> findAll(Map<String, Object> params, int pageNo, int pageSize) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<D> query = cb.createQuery(getEntityClass());
		Root<D> root = query.from(getEntityClass());

		List<Predicate> predicates = new ArrayList<>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			predicates.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
		}

		query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

		return entityManager.createQuery(query)
				.setFirstResult(pageNo * pageSize)
				.setMaxResults(pageSize)
				.getResultList();
	}

	@Override
	@Transactional
	public D updateFields(Long id, Map<String, Object> fields) {

		fields.remove("id");
		fields.remove("createdBy");
		fields.remove("createdBy");
		fields.remove("modifiedDatetime");
		fields.remove("createdDatetime");

		if (!fields.isEmpty()) {
			StringBuilder hql = new StringBuilder("update " + getEntityClassName() + " set ");

			int i = 0;
			for (String key : fields.keySet()) {
				hql.append(key).append(" = :").append(key);
				if (++i < fields.size()) {
					hql.append(", ");
				}
			}
			hql.append(" where id = :id");

			var query = entityManager.createQuery(hql.toString());
			fields.forEach(query::setParameter);
			query.setParameter("id", id);

			int updated = query.executeUpdate();
			if (updated == 0) {
				throw new ApplicationException(getEntityClassName() + " not found for id: " + id);
			}

			entityManager.clear();
		}

		return findById(id);
	}

	@Override
	public String getEntityClassName() {
		return getEntityClass().getSimpleName();
	}

}
