package com.sunilos.springboot.ctl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sunilos.springboot.bean.Document;
import com.sunilos.springboot.bean.DocumentSummary;
import com.sunilos.springboot.form.DocumentForm;
import com.sunilos.springboot.service.DocumentServiceInt;

@RestController
@RequestMapping(value = "Document")

/**
 * Document rest controllers. It contains following REST points for CRUD
 * operations and Upload and download api for a file.
 * 
 * http://localhost:8080/Document : displays health of bean an says fit and fine
 * http://localhost:8080/Document/get/1: returns Document of ID 1
 * http://localhost:8080/Document/search: returns list of Documents
 * http://localhost:8080/Document/search/pageNo: returns list of Documents with
 * pagination http://localhost:8080/Document/upload: upload file using
 * multi-part form http://localhost:8080/Document/download/1: download file for
 * give id http://localhost:8080/Document/delete/1: deletes Document of given
 * http://localhost:8080/Document/save: adds or updates a document
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */
public class DocumentCtl {

	/*
	 * @Autowired private DocumentJpaRespositoryInt repository;
	 */

	@Autowired
	private DocumentServiceInt service;

	/**
	 * Returns health of controller
	 * 
	 * @return
	 */
	@GetMapping
	public String health() {
		return "I am fit and fine! " + new Date();
	}

	/**
	 * Returns Document of given id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("get/{id}")
	public Document get(@PathVariable Long id) {
		Document m = service.findById(id);
		System.out.println("Object:------------" + m);
		return m;
	}

	/**
	 * Returns list of Documents
	 * 
	 * @return
	 */
	@PostMapping("search")
	public List<DocumentSummary> search(@RequestBody Document doc) {
		System.out.println("-----------------------" + doc.getName());
		List<DocumentSummary> list = service.search();
		return list;
	}

	@PostMapping("search/{page}")
	public List<DocumentSummary> search(@RequestBody DocumentSummary doc, @PathVariable int page) {
		System.out.println("-----------------------" + doc.getName());
		List<DocumentSummary> list = service.search(doc, page, 10);
		return list;
	}

	/**
	 * Deletes Document of given id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("delete/{id}")
	public Document delete(@PathVariable long id) {
		Document doc = service.findById(id);
		doc.setDoc(null);
		service.delete(id);
		return doc;
	}

	/**
	 * Adds or updates Document. Before saving the data form is validated.
	 * 
	 * @param form:
	 *            contains submitted data
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("save")
	public Map<String, Object> save(@RequestBody @Valid DocumentForm form, BindingResult bindingResult) {

		Map<String, Object> response = new HashMap<String, Object>();

		if (bindingResult.hasErrors()) {

			Map<String, String> errors = new HashMap<String, String>();

			List<FieldError> list = bindingResult.getFieldErrors();
			// Lambda expression Java 8 feature
			list.forEach(e -> {
				errors.put(e.getField(), e.getDefaultMessage());
			});

			response.put("success", false);
			response.put("errors", errors);

		} else {

			Document m = new Document();
			m.setId(form.getId());
			m.setName(form.getName());
			m.setType(form.getType());
			m.setDescription(form.getDescription());
			service.add(m);
			response.put("success", true);
			response.put("id", m.getId());
		}

		return response;
	}

	/**
	 * Upload a document. If ID is exist in database then document is updated
	 * else added to database.
	 * 
	 * @param id
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public Map<String, Object> upload(@RequestParam(required = false) Long id, @RequestParam("file") MultipartFile file,
			@RequestParam(required = false) String description) {

		System.out.println("Received ID " + id);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);

		try {
			Document doc = new Document();
			doc.setId(id);
			doc.setName(file.getOriginalFilename());
			doc.setType(file.getContentType());
			doc.setDoc(file.getBytes());
			doc.setDescription(description);

			long pk = 0;

			if (id != null && id > 0) {
				service.update(doc);
				pk = id;
			} else {
				pk = service.add(doc);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			response.put("id", pk);
			response.put("name", doc.getName());
			response.put("type", doc.getType());
			response.put("size", file.getSize());

		} catch (IOException e) {
			response.put("success", false);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Download a document
	 * 
	 * @param id
	 * @param response
	 */
	@GetMapping("/download/{id}")
	public @ResponseBody void download(@PathVariable long id, HttpServletResponse response) {
		Document dto = service.findById(id);
		try {
			if (dto != null) {
				response.setContentType(dto.getType());
				OutputStream out = response.getOutputStream();
				out.write(dto.getDoc());
				out.close();
			} else {
				response.getWriter().write("ERROR: File not found");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
