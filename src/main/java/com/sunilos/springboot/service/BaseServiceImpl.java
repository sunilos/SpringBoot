package com.sunilos.springboot.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.sunilos.springboot.bean.BaseDTO;

/**
 * Base service implementation. Provides CRUD operations shared by all
 * entity services, delegating to the DAO returned by {@link #getDao()}.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public abstract class BaseServiceImpl<D extends BaseDTO> implements BaseServiceInt<D> {

	@Override
	public long add(D dto) {
		return getDao().add(dto);
	}

	@Override
	public void update(D dto) {
		getDao().update(dto);
	}

	@Override
	public long save(D dto) {
		long id = dto.getId();
		if (id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}

	@Override
	public D delete(long id) {
		D dto = findById(id);
		getDao().delete(id);
		return dto;
	}

	@Override
	public D findById(long id) {
		return getDao().findById(id);
	}

	@Override
	public boolean exists(long id) {
		return getDao().exists(id);
	}

	@Override
	public List<D> search() {
		return getDao().findAll();
	}

	@Override
	public List<D> search(D dto, int pageNo, int pageSize) {
		Map<String, Object> params = new LinkedHashMap<>();
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(dto);
		for (Field field : dto.getClass().getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			Object value = wrapper.getPropertyValue(field.getName());
			if (value != null) {
				params.put(field.getName(), value);
			}
		}
		return getDao().findAll(params, pageNo - 1, pageSize);
	}

	@Override
	public D updateFields(Long id, Map<String, Object> fields) {
		return getDao().updateFields(id, fields);
	}

}
