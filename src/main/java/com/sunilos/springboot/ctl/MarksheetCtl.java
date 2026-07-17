package com.sunilos.springboot.ctl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class MarksheetCtl {

	@Autowired
	private MarksheetServiceInt service;

	// ------------------------------------------------------------------ helpers

	private ResponseEntity<Map<String, Object>> successResponse(Object data, String message, HttpStatus status) {
		Map<String, Object> res = new HashMap<>();
		res.put("error", false);
		res.put("message", message);
		res.put("data", data);
		return ResponseEntity.status(status).body(res);
	}

	private ResponseEntity<Map<String, Object>> successResponse(Object data) {
		return successResponse(data, "", HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> errorResponse(Object errors, String message, HttpStatus status) {
		Map<String, Object> res = new HashMap<>();
		res.put("error", true);
		res.put("message", message);
		res.put("errors", errors);
		return ResponseEntity.status(status).body(res);
	}

	// -------------------------------------------------------------------- GET

	/**
	 * GET /Marksheet → returns all marksheets
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> get() {
		List<Marksheet> list = service.search();
		return successResponse(list);
	}

	/**
	 * GET /Marksheet/{id} → returns single marksheet by id
	 */
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
		Marksheet m = service.findById(id);
		if (m == null) {
			return errorResponse(null, "Marksheet not found", HttpStatus.NOT_FOUND);
		}
		return successResponse(m);
	}

	/**
	 * GET /Marksheet/rollno/{rollNo} → returns marksheet by roll number
	 */
	@GetMapping("rollno/{rollNo}")
	public ResponseEntity<Map<String, Object>> getByRollNo(@PathVariable String rollNo) {
		Marksheet m = service.findByRollNo(rollNo);
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
		List<Marksheet> list = service.getMeritList();
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

	// ------------------------------------------------------------------ PATCH

	/**
	 * PATCH /Marksheet/{id} → partial update (only supplied fields are changed)
	 */
	@PatchMapping("{id}")
	public ResponseEntity<Map<String, Object>> patch(@PathVariable Long id,
			@RequestBody Map<String, Object> fields) {

		Marksheet existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "Marksheet not found", HttpStatus.NOT_FOUND);
		}

		if (fields.containsKey("rollNo"))
			existing.setRollNo((String) fields.get("rollNo"));
		if (fields.containsKey("name"))
			existing.setName((String) fields.get("name"));
		if (fields.containsKey("physics"))
			existing.setPhysics((Integer) fields.get("physics"));
		if (fields.containsKey("chemistry"))
			existing.setChemistry((Integer) fields.get("chemistry"));
		if (fields.containsKey("maths"))
			existing.setMaths((Integer) fields.get("maths"));
		if (fields.containsKey("studentId"))
			existing.setStudentId(Long.parseLong(fields.get("studentId").toString()));

		service.update(existing);
		return successResponse(existing, "Marksheet partially updated", HttpStatus.OK);
	}

	// ----------------------------------------------------------------- DELETE

	/**
	 * DELETE /Marksheet/{id} → delete marksheet
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		Marksheet existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "Marksheet not found", HttpStatus.NOT_FOUND);
		}
		service.delete(id);
		return successResponse(null, "Marksheet deleted successfully", HttpStatus.OK);
	}

	// ----------------------------------------------------------------- private

	private Map<String, String> fieldErrors(BindingResult br) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError e : br.getFieldErrors()) {
			errors.put(e.getField(), e.getDefaultMessage());
		}
		return errors;
	}
}
