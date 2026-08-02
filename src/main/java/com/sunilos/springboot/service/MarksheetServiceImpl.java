package com.sunilos.springboot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.Marksheet;
import com.sunilos.springboot.common.ApplicationException;
import com.sunilos.springboot.dao.MarksheetDAOInt;

/**
 * Marksheet service class contains business logics.
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@Service
@Transactional
public class MarksheetServiceImpl implements MarksheetServiceInt {

	private static Logger log = LoggerFactory.getLogger(MarksheetServiceImpl.class);

	@Autowired
	private MarksheetDAOInt dao;

	@Override
	public long add(Marksheet dto) {
		return dao.add(dto);
	}

	@Override
	public void update(Marksheet dto) {
		dao.update(dto);
	}

	@Override
	public long save(Marksheet dto) {

		long id = dto.getId();

		if (id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}

	@Override
	public Marksheet delete(long id) {
		Marksheet m = findById(id);
		dao.delete(id);
		return m;
	}

	@Override
	public Marksheet findById(long id) {
		Marksheet m = dao.findById(id);
		if (m == null) {
			log.error("Marksheet not found");
			throw new ApplicationException("Marksheet not found");
		}
		return m;
	}

	@Override
	public List<Marksheet> search() {
		return dao.findAll();
	}

	@Override
	public List<Marksheet> search(Marksheet dto, int pageNo, int pageSize) {

		Map<String, Object> params = new HashMap<>();
		if (dto.getRollNo() != null) {
			params.put("rollNo", dto.getRollNo());
		}
		if (dto.getName() != null) {
			params.put("name", dto.getName());
		}
		if (dto.getPhysics() != null) {
			params.put("physics", dto.getPhysics());
		}
		if (dto.getChemistry() != null) {
			params.put("chemistry", dto.getChemistry());
		}
		if (dto.getMaths() != null) {
			params.put("maths", dto.getMaths());
		}
		if (dto.getStudentId() != null) {
			params.put("studentId", dto.getStudentId());
		}

		return dao.findAll(params, pageNo, pageSize);
	}

	@Override
	public Marksheet findByRollNo(String rollNo) {
		return dao.findByRollNo(rollNo);
	}

	@Override
	public boolean exists(long id) {
		return dao.exists(id);
	}

	@Override
	public List<Marksheet> getMeritList() {
		return dao.getMeritList();
	}

	@Override
	public Marksheet updateFields(Long id, Map<String, Object> fields) {
		return dao.updateFields(id, fields);
	}

}
