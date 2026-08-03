package com.sunilos.springboot.service;

import java.util.List;
import java.util.Map;
import com.sunilos.springboot.bean.BaseDTO;
import com.sunilos.springboot.dao.BaseDAOInt;

/**
 * Base service interface. Defines basic CRUD operations shared by all
 * entity services.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public interface BaseServiceInt<D extends BaseDTO> {

	/**
	 * Adds an entity
	 *
	 * @param dto
	 * @return generated id of the persisted entity
	 */
	public long add(D dto);

	/**
	 * Updates an entity
	 *
	 * @param dto
	 */
	public void update(D dto);

	/**
	 * Updates the entity if its ID exists, otherwise adds it
	 *
	 * @param dto
	 * @return id of the saved entity
	 */
	public long save(D dto);

	/**
	 * Deletes an entity by id
	 *
	 * @param id
	 * @return the deleted entity
	 */
	public D delete(long id);

	/**
	 * Finds an entity by id
	 *
	 * @param id
	 * @return
	 */
	public D findById(long id);

	public boolean exists(long id);

	/**
	 * Returns list of all entities
	 *
	 * @return
	 */
	public List<D> search();

	/**
	 * Returns list of entities matching the given criteria, with pagination
	 *
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<D> search(D dto, int pageNo, int pageSize);

	public abstract BaseDAOInt<D> getDao();

	public D updateFields(Long id, Map<String, Object> fields);

}
