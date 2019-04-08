package com.sunilos.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunilos.springboot.bean.Document;
import com.sunilos.springboot.bean.Marksheet;

/**
 * 
 * Repository provides CRUD for document information and binary data
 * 
 * JpaSpecificationExecutor is used to for dynamic custom queries
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@Repository
public interface DocumentRespositoryInt extends JpaRepository<Document, Long>, JpaSpecificationExecutor {

	/**
	 * Finds document by name
	 * 
	 * @param rollNo
	 * @return
	 */
	public List<Document> findByName(String name);

}
