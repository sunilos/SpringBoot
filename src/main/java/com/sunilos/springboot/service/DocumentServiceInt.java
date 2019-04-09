package com.sunilos.springboot.service;

import java.util.List;

import com.sunilos.springboot.bean.Document;
import com.sunilos.springboot.bean.DocumentSummary;

/**
 * Document service interface.
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

public interface DocumentServiceInt {

	/**
	 * Adds Document
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 */

	public long add(Document dto);

	/**
	 * Updates Document
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 */
	public void update(Document dto);

	/**
	 * Updates a Document if ID does exist otherwise add the Document
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 */
	public long save(Document dto);

	/**
	 * Deletes a Document
	 * 
	 * @param id
	 */
	public Document delete(long id);

	/**
	 * Finds a Document by ID
	 * 
	 * @param id
	 * @return
	 */
	public Document findById(long id);

	/**
	 * Returns list of Documents
	 * 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<DocumentSummary> search(DocumentSummary dto);

	/**
	 * Returns list with pagination
	 * 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<DocumentSummary> search(DocumentSummary dto, int pageNo, int pageSize);

	/**
	 * Finds Document by Roll No
	 * 
	 * @param rollNo
	 * @return
	 */
	public List<DocumentSummary> findByName(String name);

}
