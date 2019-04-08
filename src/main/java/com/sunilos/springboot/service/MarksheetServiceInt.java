package com.sunilos.springboot.service;

import java.util.List;

import com.sunilos.springboot.bean.Marksheet;

/**
 * Marksheet service interface.
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

public interface MarksheetServiceInt {

	/**
	 * Adds Marksheet
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 */

	public long add(Marksheet dto);

	/**
	 * Updates Marksheet
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 */
	public void update(Marksheet dto);

	/**
	 * Updates a marksheet if ID does exist otherwise add the marksheet
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 */
	public long save(Marksheet dto);

	/**
	 * Deletes a Marksheet
	 * 
	 * @param id
	 */
	public Marksheet delete(long id);

	/**
	 * Finds a MArksheet by ID
	 * 
	 * @param id
	 * @return
	 */
	public Marksheet findById(long id);

	/**
	 * Returns list of marksheets
	 * 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Marksheet> search();

	/**
	 * Returns list with pagination
	 * 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Marksheet> search(Marksheet dto, int pageNo, int pageSize);

	/**
	 * Finds marksheet by Roll No
	 * 
	 * @param rollNo
	 * @return
	 */
	public Marksheet findByRollNo(String rollNo);

	/**
	 * Gets merit list of students
	 * 
	 * @return
	 */
	public List<Marksheet> getMeritList();
}
