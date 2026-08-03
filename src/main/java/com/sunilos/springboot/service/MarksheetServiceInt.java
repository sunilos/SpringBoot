package com.sunilos.springboot.service;

import java.util.List;
import java.util.Map;

import com.sunilos.springboot.bean.Marksheet;

/**
 * Marksheet service interface.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

public interface MarksheetServiceInt extends BaseServiceInt<Marksheet> {

	/**
	 * Finds marksheet by Roll No
	 *
	 * @param rollNo
	 * @return
	 */
	public Marksheet findByRollNo(String rollNo);

	public boolean exists(long id);

	/**
	 * Gets merit list of students
	 *
	 * @return
	 */
	public List<Marksheet> getMeritList();

	/**
	 * Updates the given fields of a marksheet identified by id
	 *
	 * @param id
	 * @param fields
	 * @return updated marksheet
	 */
	public Marksheet updateFields(Long id, Map<String, Object> fields);
}
