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

import com.sunilos.springboot.bean.User;
import com.sunilos.springboot.form.UserForm;
import com.sunilos.springboot.service.UserServiceInt;
import com.sunilos.springboot.ctl.JwtUtil;

/**
 * User REST controller.
 *
 * GET /user → list all users
 * GET /user/{id} → get by id
 * GET /user/login/{loginId} → get by login ID
 * GET /user/email/{email} → get by email
 * POST /user → create (201)
 * POST /user/search → filtered search with optional pagination
 * POST /user/changePassword/{id}→ change password
 * PUT /user/{id} → full update
 * PATCH /user/{id} → partial update (only supplied fields)
 * DELETE /user/{id} → delete
 *
 * Response envelope:
 * success: { "error": false, "message": "...", "data": ... }
 * error: { "error": true, "message": "...", "errors": ... }
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@RestController
@RequestMapping("user")
public class UserCtl {

	@Autowired
	private UserServiceInt service;

	@Autowired
	private JwtUtil jwtUtil;

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
	 * GET /User → returns all users
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> get() {
		List<User> list = service.search();
		return successResponse(list);
	}

	/**
	 * GET /User/{id} → returns single user by id
	 */
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
		User u = service.findById(id);
		if (u == null) {
			return errorResponse(null, "User not found", HttpStatus.NOT_FOUND);
		}
		return successResponse(u);
	}

	/**
	 * GET /User/login/{loginId} → returns user by login ID
	 */
	@GetMapping("login/{loginId}")
	public ResponseEntity<Map<String, Object>> getByLoginId(@PathVariable String loginId) {
		User u = service.findByLoginId(loginId);
		if (u == null) {
			return errorResponse(null, "User not found for login: " + loginId, HttpStatus.NOT_FOUND);
		}
		return successResponse(u);
	}

	/**
	 * GET /User/email/{email} → returns user by email
	 */
	@GetMapping("email/{email}")
	public ResponseEntity<Map<String, Object>> getByEmail(@PathVariable String email) {
		User u = service.findByEmail(email);
		if (u == null) {
			return errorResponse(null, "User not found for email: " + email, HttpStatus.NOT_FOUND);
		}
		return successResponse(u);
	}

	// ------------------------------------------------------------------- POST

	/**
	 * POST /User/login → authenticate and return a JWT token.
	 *
	 * Body: { "loginId": "...", "password": "..." }
	 * Response: { "error": false, "token": "...", "data": { user } }
	 */
	@PostMapping("login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
		String loginId = body.get("loginId");
		String password = body.get("password");

		System.out.println("Login attempt: loginId=" + loginId + ", password=" + password);

		if (loginId == null || password == null) {
			return errorResponse(null, "loginId and password are required", HttpStatus.BAD_REQUEST);
		}

		User u = service.findByLoginId(loginId);
		if (u == null || !password.equals(u.getPassword())) {
			return errorResponse(null, "Invalid login ID or password", HttpStatus.UNAUTHORIZED);
		}

		System.out.println("Login successful: loginId=" + loginId);

		String token = jwtUtil.generateToken(loginId);

		Map<String, Object> res = new HashMap<>();
		res.put("error", false);
		res.put("message", "Login successful");
		res.put("token", token);
		res.put("data", u);
		return ResponseEntity.ok(res);
	}

	/**
	 * POST /User → create a new user (returns 201)
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> post(@RequestBody @Valid UserForm form,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return errorResponse(fieldErrors(bindingResult), "Validation failed", HttpStatus.BAD_REQUEST);
		}

		User u = new User();
		populateBean(u, form);

		service.add(u);
		return successResponse(u, "User saved successfully", HttpStatus.CREATED);
	}

	/**
	 * POST /User/search → filtered search with optional pagination.
	 *
	 * Body (all fields optional):
	 * {
	 * "firstName": "Rahul",
	 * "loginId": "rahul01",
	 * "role": "Admin",
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

		List<User> list;
		if (pageNo > 0) {
			User criteria = new User();
			if (body.containsKey("firstName"))
				criteria.setFirstName((String) body.get("firstName"));
			if (body.containsKey("lastName"))
				criteria.setLastName((String) body.get("lastName"));
			if (body.containsKey("loginId"))
				criteria.setLoginId((String) body.get("loginId"));
			if (body.containsKey("email"))
				criteria.setEmail((String) body.get("email"));
			if (body.containsKey("role"))
				criteria.setRole((String) body.get("role"));
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

	/**
	 * POST /User/changePassword/{id} → change user password.
	 *
	 * Body: { "oldPassword": "...", "newPassword": "..." }
	 */
	@PostMapping("changePassword/{id}")
	public ResponseEntity<Map<String, Object>> changePassword(@PathVariable Long id,
			@RequestBody Map<String, String> body) {

		String oldPassword = body.get("oldPassword");
		String newPassword = body.get("newPassword");

		if (oldPassword == null || newPassword == null || newPassword.length() < 6) {
			return errorResponse(null, "oldPassword and newPassword (min 6 chars) are required",
					HttpStatus.BAD_REQUEST);
		}

		boolean changed = service.changePassword(id, oldPassword, newPassword);
		if (!changed) {
			return errorResponse(null, "Password change failed: user not found or old password incorrect",
					HttpStatus.BAD_REQUEST);
		}
		return successResponse(null, "Password changed successfully", HttpStatus.OK);
	}

	// -------------------------------------------------------------------- PUT

	/**
	 * PUT /User/{id} → full update (all fields replaced)
	 */
	@PutMapping("{id}")
	public ResponseEntity<Map<String, Object>> put(@PathVariable Long id,
			@RequestBody @Valid UserForm form, BindingResult bindingResult) {

		User existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "User not found", HttpStatus.NOT_FOUND);
		}

		if (bindingResult.hasErrors()) {
			return errorResponse(fieldErrors(bindingResult), "Validation failed", HttpStatus.BAD_REQUEST);
		}

		populateBean(existing, form);
		service.update(existing);
		return successResponse(existing, "User updated successfully", HttpStatus.OK);
	}

	// ------------------------------------------------------------------ PATCH

	/**
	 * PATCH /User/{id} → partial update (only supplied fields are changed)
	 */
	@PatchMapping("{id}")
	public ResponseEntity<Map<String, Object>> patch(@PathVariable Long id,
			@RequestBody Map<String, Object> fields) {

		User existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "User not found", HttpStatus.NOT_FOUND);
		}

		if (fields.containsKey("firstName"))
			existing.setFirstName((String) fields.get("firstName"));
		if (fields.containsKey("lastName"))
			existing.setLastName((String) fields.get("lastName"));
		if (fields.containsKey("email"))
			existing.setEmail((String) fields.get("email"));
		if (fields.containsKey("mobile"))
			existing.setMobile((String) fields.get("mobile"));
		if (fields.containsKey("gender"))
			existing.setGender((String) fields.get("gender"));
		if (fields.containsKey("role"))
			existing.setRole((String) fields.get("role"));
		if (fields.containsKey("status"))
			existing.setStatus(Integer.parseInt(fields.get("status").toString()));

		service.update(existing);
		return successResponse(existing, "User partially updated", HttpStatus.OK);
	}

	// ----------------------------------------------------------------- DELETE

	/**
	 * DELETE /User/{id} → delete user
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		User existing = service.findById(id);
		if (existing == null) {
			return errorResponse(null, "User not found", HttpStatus.NOT_FOUND);
		}
		service.delete(id);
		return successResponse(null, "User deleted successfully", HttpStatus.OK);
	}

	// ----------------------------------------------------------------- private

	private void populateBean(User u, UserForm form) {
		u.setLoginId(form.getLoginId());
		u.setPassword(form.getPassword());
		u.setFirstName(form.getFirstName());
		u.setLastName(form.getLastName());
		u.setEmail(form.getEmail());
		u.setMobile(form.getMobile());
		u.setDob(form.getDob());
		u.setGender(form.getGender());
		u.setRole(form.getRole());
		u.setStatus(form.getStatus());
	}

	/**
	 * Extracts field-level validation errors from the binding result.
	 * 
	 * @param br
	 * @return
	 */
	private Map<String, String> fieldErrors(BindingResult br) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError e : br.getFieldErrors()) {
			errors.put(e.getField(), e.getDefaultMessage());
		}
		return errors;
	}
}
