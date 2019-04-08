package com.sunilos.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.Document;
import com.sunilos.springboot.bean.DocumentSummary;
import com.sunilos.springboot.bean.Marksheet;

/**
 * 
 * Marksheet Data Access Object (DAO) interface.
 * 
 * JpaSpecificationExecutor is used to for dynamic custom queries
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@Repository
public interface DocumentSummaryRespositoryInt extends JpaRepository<DocumentSummary, Long>, JpaSpecificationExecutor {

	/**
	 * Finds document by name
	 * 
	 * @param rollNo
	 * @return
	 */
	public List<DocumentSummary> findByName(String name);

}
