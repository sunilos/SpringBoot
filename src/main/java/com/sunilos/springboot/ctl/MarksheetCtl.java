package com.sunilos.springboot.ctl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunilos.springboot.bean.Marksheet;
import com.sunilos.springboot.form.MarksheetForm;
import com.sunilos.springboot.service.MarksheetServiceInt;

/**
 * Marksheet REST controller.
 *
 * GET /marksheet → list all marksheets
 * GET /marksheet/{id} → get by id
 * GET /marksheet/rollno/{no} → get by roll number
 * GET /marksheet/meritlist → merit list ordered by total marks
 * POST /marksheet → create (201)
 * POST /marksheet/search → filtered search with optional pagination
 * PUT /marksheet/{id} → full update
 * PATCH /marksheet/{id} → partial update (only supplied fields)
 * DELETE /marksheet/{id} → delete
 *
 * Response envelope (mirrors Python BaseRestCtl):
 * success: { "error": false, "message": "...", "data": ... }
 * error: { "error": true, "message": "...", "errors": ... }
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@RestController
@RequestMapping("marksheet")
public class MarksheetCtl extends BaseCtl<Marksheet> {

	@Autowired
	private MarksheetServiceInt service;

	@Autowired
	private Validator validator;

	@Override
	public MarksheetServiceInt getService() {
		return service;
	}

	/**
	 * GET /Marksheet/rollno/{rollNo} → returns marksheet by roll number
	 */
	@GetMapping("rollno/{rollNo}")
	public ResponseEntity<Map<String, Object>> getByRollNo(@PathVariable String rollNo) {
		Marksheet m = getService().findByRollNo(rollNo);
		if (m == null) {
			return errorResponse(null, "Marksheet not found for roll no: " + rollNo, HttpStatus.NOT_FOUND);
		}
		return successResponse(m);
	}

	/**
	 * GET /Marksheet/meritlist → merit list ordered by total marks
	 */
	@GetMapping("meritlist")
	public ResponseEntity<Map<String, Object>> getMeritList() {
		List<Marksheet> list = getService().getMeritList();
		return successResponse(list);
	}

	// ------------------------------------------------------------------- POST

	/**
	 * POST /Marksheet → create a new marksheet (returns 201)
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> post(@RequestBody @Valid MarksheetForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return errorResponse(fieldErrors(bindingResult), "Validation failed", HttpStatus.BAD_REQUEST);
		}

		Marksheet m = new Marksheet();
		m.setStudentId(form.getStudentId());
		m.setRollNo(form.getRollNo());
		m.setName(form.getName());
		m.setPhysics(form.getPhysics());
		m.setChemistry(form.getChemistry());
		m.setMaths(form.getMaths());

		service.add(m);
		return successResponse(m, "Marksheet saved successfully", HttpStatus.CREATED);
	}

	/**
	 * POST /Marksheet/search → filtered search with optional pagination.
	 *
	 * Body (all fields optional):
	 * {
	 * "name": "Rahul",
	 * "rollNo": "A1",
	 * "pageNo": 1,
	 * "pageSize": 10
	 * }
	 * When pageNo is omitted or 0, all matching records are returned.
	 */
	@PostMapping("search")
	public ResponseEntity<Map<String, Object>> search(@RequestBody(required = false) Map<String, Object> body) {
		if (body == null) {
			body = new HashMap<>();
		}

		int pageNo = body.containsKey("pageNo") ? Integer.parseInt(body.get("pageNo").toString()) : 0;
		int pageSize = body.containsKey("pageSize") ? Integer.parseInt(body.get("pageSize").toString()) : 10;

		List<Marksheet> list;
		if (pageNo > 0) {
			Marksheet criteria = new Marksheet();
			if (body.containsKey("name"))
				criteria.setName((String) body.get("name"));
			if (body.containsKey("rollNo"))
				criteria.setRollNo((String) body.get("rollNo"));
			list = service.search(criteria, pageNo, pageSize);
		} else {
			list = service.search();
		}

		Map<String, Object> res = new HashMap<>();
		res.put("error", false);
		res.put("message", "");
		res.put("data", list);
		if (pageNo > 0) {
			Map<String, Object> pagination = new HashMap<>();
			pagination.put("pageNo", pageNo);
			pagination.put("pageSize", pageSize);
			res.put("pagination", pagination);
		}
		return ResponseEntity.ok(res);
	}

	// -------------------------------------------------------------------- PUT

	/**
	 * PUT /Marksheet/{id} → full update (all fields replaced)
	 */
	@PutMapping("{id}")
	public ResponseEntity<Map<String, Object>> put(@PathVariable Long id,
			@RequestBody @Valid MarksheetForm form, BindingResult bindingResult) {

		Marksheet existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "Marksheet not found", HttpStatus.NOT_FOUND);
		}

		if (bindingResult.hasErrors()) {
			return errorResponse(fieldErrors(bindingResult), "Validation failed", HttpStatus.BAD_REQUEST);
		}

		existing.setStudentId(form.getStudentId());
		existing.setRollNo(form.getRollNo());
		existing.setName(form.getName());
		existing.setPhysics(form.getPhysics());
		existing.setChemistry(form.getChemistry());
		existing.setMaths(form.getMaths());

		service.update(existing);
		return successResponse(existing, "Marksheet updated successfully", HttpStatus.OK);
	}

	/**
	 * PATCH /Marksheet/{id} → partial update (only supplied fields)
	 *
	 * @param id     marksheet id
	 * @param fields fields to update
	 * @return updated fields
	 */
	@PatchMapping("{id}")
	public ResponseEntity<Map<String, Object>> patch(@PathVariable Long id,
			@RequestBody Map<String, Object> fields) {

		if (!service.exists(id)) {
			return errorResponse(null, "Marksheet not found", HttpStatus.NOT_FOUND);
		}

		// Validate the supplied fields using a MarksheetForm and the same validator
		// used for POST/PUT
		MarksheetForm form = new MarksheetForm();
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(form);
		for (Map.Entry<String, Object> entry : fields.entrySet()) {
			if (wrapper.isWritableProperty(entry.getKey())) {
				wrapper.setPropertyValue(entry.getKey(), entry.getValue());
			}
		}

		DataBinder binder = new DataBinder(form, "form");
		binder.setValidator(validator);
		binder.validate();
		BindingResult bindingResult = binder.getBindingResult();

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = fieldErrors(bindingResult);
			errors.keySet().retainAll(fields.keySet()); // only supplied fields can fail
			if (!errors.isEmpty()) {
				return errorResponse(errors, "Validation failed", HttpStatus.BAD_REQUEST);
			}
		}

		service.updateFields(id, fields);

		return successResponse(fields, "Marksheet partially updated", HttpStatus.OK);
	}
}
