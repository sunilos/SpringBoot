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

import com.sunilos.springboot.bean.Role;
import com.sunilos.springboot.form.RoleForm;
import com.sunilos.springboot.service.RoleServiceInt;

/**
 * Role REST controller.
 *
 * GET    /Role                 → list all roles
 * GET    /Role/{id}            → get by id
 * GET    /Role/name/{name}     → get by name
 * POST   /Role                 → create  (201)
 * POST   /Role/search          → filtered search with optional pagination
 * PUT    /Role/{id}            → full update
 * PATCH  /Role/{id}            → partial update (only supplied fields)
 * DELETE /Role/{id}            → delete
 *
 * Response envelope:
 *   success: { "error": false, "message": "...", "data": ... }
 *   error:   { "error": true,  "message": "...", "errors": ... }
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@RestController
@RequestMapping("Role")
public class RoleCtl {

	@Autowired
	private RoleServiceInt service;

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
	 * GET /Role → returns all roles
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> get() {
		List<Role> list = service.search();
		return successResponse(list);
	}

	/**
	 * GET /Role/{id} → returns single role by id
	 */
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
		Role r = service.findById(id);
		if (r == null) {
			return errorResponse(null, "Role not found", HttpStatus.NOT_FOUND);
		}
		return successResponse(r);
	}

	/**
	 * GET /Role/name/{name} → returns role by name
	 */
	@GetMapping("name/{name}")
	public ResponseEntity<Map<String, Object>> getByName(@PathVariable String name) {
		Role r = service.findByName(name);
		if (r == null) {
			return errorResponse(null, "Role not found: " + name, HttpStatus.NOT_FOUND);
		}
		return successResponse(r);
	}

	// ------------------------------------------------------------------- POST

	/**
	 * POST /Role → create a new role (returns 201)
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> post(@RequestBody @Valid RoleForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return errorResponse(fieldErrors(bindingResult), "Validation failed", HttpStatus.BAD_REQUEST);
		}

		Role r = new Role();
		r.setName(form.getName());
		r.setDescription(form.getDescription());

		service.add(r);
		return successResponse(r, "Role saved successfully", HttpStatus.CREATED);
	}

	/**
	 * POST /Role/search → filtered search with optional pagination.
	 *
	 * Body (all fields optional):
	 * {
	 *   "name":     "Admin",
	 *   "pageNo":   1,
	 *   "pageSize": 10
	 * }
	 * When pageNo is omitted or 0, all matching records are returned.
	 */
	@PostMapping("search")
	public ResponseEntity<Map<String, Object>> search(@RequestBody(required = false) Map<String, Object> body) {
		if (body == null) {
			body = new HashMap<>();
		}

		int pageNo   = body.containsKey("pageNo")   ? Integer.parseInt(body.get("pageNo").toString())   : 0;
		int pageSize = body.containsKey("pageSize")  ? Integer.parseInt(body.get("pageSize").toString()) : 10;

		List<Role> list;
		if (pageNo > 0) {
			Role criteria = new Role();
			if (body.containsKey("name"))        criteria.setName((String) body.get("name"));
			if (body.containsKey("description")) criteria.setDescription((String) body.get("description"));
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
	 * PUT /Role/{id} → full update (all fields replaced)
	 */
	@PutMapping("{id}")
	public ResponseEntity<Map<String, Object>> put(@PathVariable Long id,
			@RequestBody @Valid RoleForm form, BindingResult bindingResult) {

		Role existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "Role not found", HttpStatus.NOT_FOUND);
		}

		if (bindingResult.hasErrors()) {
			return errorResponse(fieldErrors(bindingResult), "Validation failed", HttpStatus.BAD_REQUEST);
		}

		existing.setName(form.getName());
		existing.setDescription(form.getDescription());

		service.update(existing);
		return successResponse(existing, "Role updated successfully", HttpStatus.OK);
	}

	// ------------------------------------------------------------------ PATCH

	/**
	 * PATCH /Role/{id} → partial update (only supplied fields are changed)
	 */
	@PatchMapping("{id}")
	public ResponseEntity<Map<String, Object>> patch(@PathVariable Long id,
			@RequestBody Map<String, Object> fields) {

		Role existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "Role not found", HttpStatus.NOT_FOUND);
		}

		if (fields.containsKey("name"))        existing.setName((String) fields.get("name"));
		if (fields.containsKey("description")) existing.setDescription((String) fields.get("description"));

		service.update(existing);
		return successResponse(existing, "Role partially updated", HttpStatus.OK);
	}

	// ----------------------------------------------------------------- DELETE

	/**
	 * DELETE /Role/{id} → delete role
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		Role existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "Role not found", HttpStatus.NOT_FOUND);
		}
		service.delete(id);
		return successResponse(null, "Role deleted successfully", HttpStatus.OK);
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
