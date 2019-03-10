package com.sunilos.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.Marksheet;

/**
 * 
 * Marksheet Data Access Object (DAO) interface.
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@Repository
public interface MarksheetRespositoryInt extends JpaRepository<Marksheet, Long> {

	/**
	 * Finds marksheet by roll number
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
	@Query("from Marksheet order by (physics + chemistry + maths) desc")
	public List<Marksheet> getMeritList();

}
