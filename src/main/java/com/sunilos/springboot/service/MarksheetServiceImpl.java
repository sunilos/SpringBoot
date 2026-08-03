package com.sunilos.springboot.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.Marksheet;
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
public class MarksheetServiceImpl extends BaseServiceImpl<Marksheet> implements MarksheetServiceInt {

	private static Logger log = LoggerFactory.getLogger(MarksheetServiceImpl.class);

	@Autowired
	private MarksheetDAOInt dao;

	@Override
	public MarksheetDAOInt getDao() {
		return dao;
	}

	@Override
	public Marksheet findByRollNo(String rollNo) {
		return dao.findByRollNo(rollNo);
	}

	@Override
	public List<Marksheet> getMeritList() {
		return getDao().getMeritList();
	}

}
