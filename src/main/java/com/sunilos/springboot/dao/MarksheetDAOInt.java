package com.sunilos.springboot.dao;

import com.sunilos.springboot.bean.Marksheet;
import java.util.List;

/**
 * Marksheet DAO interface. Defines basic CRUD operations backed directly by
 * an EntityManager implementation.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

public interface MarksheetDAOInt extends BaseDAOInt<Marksheet> {
	/**
	 * Finds marksheet by roll number
	 *
	 * @param rollNo
	 * @return
	 */
	public Marksheet findByRollNo(String rollNo);

	/**
	 * Gets merit list of students ordered by total marks descending
	 *
	 * @return
	 */
	public List<Marksheet> getMeritList();
}
