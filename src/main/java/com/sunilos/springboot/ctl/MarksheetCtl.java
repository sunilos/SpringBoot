package com.sunilos.springboot.ctl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sunilos.springboot.bean.Marksheet;
import com.sunilos.springboot.form.MarksheetForm;
import com.sunilos.springboot.service.MarksheetServiceInt;

@RestController
@RequestMapping(value = "Marksheet")

/**
 * Marksheet rest controllers. It contains following REST points
 * 
 * http://localhost:8080/Marksheet : displays health of bean an says fit and
 * fine http://localhost:8080/Marksheet/get/1 : returns marksheet of ID 1
 * http://localhost:8080/Marksheet/search : returns list of marksheets
 * http://localhost:8080/Marksheet/rollno/A1 : returns Marksheet of given A1
 * roll number http://localhost:8080/Marksheet/meritlist : returns merit list of
 * students http://localhost:8080/Marksheet/delete/1 : deletes marksheet of
 * given id http://localhost:8080/Marksheet/save : adds or updates a markhseet
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 *
 */
public class MarksheetCtl {

	/*
	 * @Autowired private MarksheetJpaRespositoryInt repository;
	 */

	@Autowired
	private MarksheetServiceInt service;

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
	 * Returns marksheet of given id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("get/{id}")
	public Marksheet get(@PathVariable Long id) {
		Marksheet m = service.findById(id);
		System.out.println("Object:------------" + m);
		return m;
	}

	/**
	 * Returns list of marksheets
	 * 
	 * @return
	 */
	@RequestMapping(value = "search", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Marksheet> search(@RequestBody Marksheet m) {
		System.out.println("-----------------------" + m.getName());
		List<Marksheet> list = service.search();
		return list;
	}

	@RequestMapping(value = "search/{page}", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Marksheet> search(@RequestBody Marksheet m, @PathVariable int page) {
		System.out.println("-----------------------" + m.getName());
		List<Marksheet> list = service.search(m, page, 10);
		return list;
	}

	/**
	 * Returns marksheet of given roll number
	 * 
	 * @param rollNo
	 * @return
	 */

	@GetMapping("rollno/{rollNo}")
	public Marksheet get(@PathVariable String rollNo) {
		Marksheet m = service.findByRollNo(rollNo);
		return m;
	}

	/**
	 * Returns merit list of students
	 * 
	 * @return
	 */

	@GetMapping("meritlist")
	public List<Marksheet> getMeritList() {
		List<Marksheet> list = service.getMeritList();
		return list;
	}

	/**
	 * Deletes marksheet of given id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("delete/{id}")
	public Marksheet delete(@PathVariable long id) {
		Marksheet m = service.findById(id);
		service.delete(id);
		return m;
	}

	/**
	 * Adds or updates Marksheet. Before saving the data form is validated.
	 * 
	 * @param form:
	 *            contains submitted data
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("save")
	public Map<String, Object> save(@RequestBody @Valid MarksheetForm form, BindingResult bindingResult) {

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

			Marksheet m = new Marksheet();
			m.setId(form.getId());
			m.setStudentId(form.getStudentId());
			m.setRollNo(form.getRollNo());
			m.setName(form.getName());
			m.setPhysics(form.getPhysics());
			m.setChemistry(form.getChemistry());
			m.setMaths(form.getMaths());

			service.add(m);

			response.put("success", true);
			response.put("id", m.getId());

		}

		return response;
	}

}
