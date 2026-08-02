package com.sunilos.springboot.dao;

import java.util.List;
import java.util.Map;
import com.sunilos.springboot.bean.BaseDTO;

/**
 * Marksheet DAO interface. Defines basic CRUD operations backed directly by
 * an EntityManager implementation.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */

public interface BaseDAOInt<D extends BaseDTO> {

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
	 * Deletes an entity by id
	 *
	 * @param id
	 */
	public void delete(long id);

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
	public List<D> findAll();

	/**
	 * Finds all marksheets matching the given parameters. The params map contains
	 * field names as keys and their corresponding values to filter by.
	 * 
	 * @param params
	 * @return
	 */
	public List<D> findAll(Map<String, Object> params);

	/**
	 * Finds all marksheets matching the given parameters with pagination. The
	 * params map contains
	 * field names as keys and their corresponding values to filter by.
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<D> findAll(Map<String, Object> params, int pageNo, int pageSize);

	/**
	 * Updates the given fields of a marksheet identified by id. Only the
	 * supported keys (rollNo, name, physics, chemistry, maths, studentId) are
	 * applied; unknown keys in the map are ignored.
	 *
	 * @param id     marksheet id to update
	 * @param fields map of field name to new value
	 * @return updated marksheet
	 */
	public D updateFields(Long id, Map<String, Object> fields);

	public abstract Class<D> getEntityClass();

	public String getEntityClassName();

}
