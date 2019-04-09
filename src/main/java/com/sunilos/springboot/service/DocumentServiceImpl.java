package com.sunilos.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunilos.springboot.bean.Document;
import com.sunilos.springboot.bean.DocumentSummary;
import com.sunilos.springboot.dao.DocumentRespositoryInt;
import com.sunilos.springboot.dao.DocumentSummaryRespositoryInt;

/**
 * Document service class contains business logics.
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 * 
 */

@Service
@Transactional
public class DocumentServiceImpl implements DocumentServiceInt {

	private static Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

	@Autowired
	private DocumentRespositoryInt dao;

	@Autowired
	private DocumentSummaryRespositoryInt summaryDao;

	@Override
	public long add(Document dto) {
		Document m = dao.save(dto);
		return m.getId();
	}

	@Override
	public void update(Document dto) {
		dao.save(dto);
	}

	@Override
	public long save(Document dto) {

		long id = dto.getId();

		if (id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}

	@Override
	public Document delete(long id) {
		Document m = findById(id);
		dao.deleteById(id);
		return m;
	}

	@Override
	@Transactional(readOnly = true)
	public Document findById(long id) {
		Optional<Document> optional = dao.findById(id);
		return optional.get();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DocumentSummary> search(DocumentSummary dto) {
		Example<DocumentSummary> example = Example.of(dto);
		return summaryDao.findAll(example);
	}

	@Override

	@Transactional(readOnly = true)
	public List<DocumentSummary> search(DocumentSummary dto, int pageNo, int pageSize) {

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

		Example<DocumentSummary> example = Example.of(dto);

		Page<DocumentSummary> page = null;

		if (example != null) {
			page = summaryDao.findAll(example, pageRequest);
		} else {
			page = summaryDao.findAll(pageRequest);
		}

		return page.getContent();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DocumentSummary> findByName(String name) {
		return summaryDao.findByName(name);
	}

}
