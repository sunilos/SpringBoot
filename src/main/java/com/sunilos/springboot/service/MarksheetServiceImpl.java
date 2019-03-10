package com.sunilos.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.Marksheet;
import com.sunilos.springboot.dao.MarksheetRespositoryInt;

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
	private MarksheetRespositoryInt dao;

	@Override
	public long add(Marksheet dto) {
		Marksheet m = dao.save(dto);
		return m.getId();
	}

	@Override
	public void update(Marksheet dto) {
		dao.save(dto);
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
		dao.deleteById(id);
		return m;
	}

	@Override
	public Marksheet findById(long id) {
		Optional<Marksheet> optional = dao.findById(id);
		return optional.get();
	}

	@Override
	public List<Marksheet> search() {
		return dao.findAll();
	}

	@Override
	public Marksheet findByRollNo(String rollNo) {
		return dao.findByRollNo(rollNo);
	}

	@Override
	public List<Marksheet> getMeritList() {
		return dao.getMeritList();
	}

}