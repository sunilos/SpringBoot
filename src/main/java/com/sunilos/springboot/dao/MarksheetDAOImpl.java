package com.sunilos.springboot.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.sunilos.springboot.bean.Marksheet;
import jakarta.persistence.NoResultException;

/**
 * Marksheet DAO implementation. Performs CRUD operations directly through
 * the JPA EntityManager instead of a Spring Data repository.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

@Repository
public class MarksheetDAOImpl extends BaseDAOImpl<Marksheet> implements MarksheetDAOInt {

	@Override
	public Class<Marksheet> getEntityClass() {
		return Marksheet.class;
	}

	@Override
	public Marksheet findByRollNo(String rollNo) {
		try {
			return entityManager.createQuery("from Marksheet where rollNo = :rollNo", Marksheet.class)
					.setParameter("rollNo", rollNo)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Marksheet> getMeritList() {
		return entityManager.createQuery("from Marksheet order by (physics + chemistry + maths) desc", Marksheet.class)
				.getResultList();
	}

}
