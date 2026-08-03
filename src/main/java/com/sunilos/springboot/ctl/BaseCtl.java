package com.sunilos.springboot.ctl;

import com.sunilos.springboot.bean.BaseDTO;
import com.sunilos.springboot.service.BaseServiceInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Base REST controller. Holds response-envelope helpers shared by all
 * controllers.
 *
 * Response envelope (mirrors Python BaseRestCtl):
 * success: { "error": false, "message": "...", "data": ... }
 * error: { "error": true, "message": "...", "errors": ... }
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public abstract class BaseCtl<D extends BaseDTO> {

	protected ResponseEntity<Map<String, Object>> successResponse(Object data, String message, HttpStatus status) {
		Map<String, Object> res = new HashMap<>();
		res.put("error", false);
		res.put("message", message);
		res.put("data", data);
		return ResponseEntity.status(status).body(res);
	}

	protected ResponseEntity<Map<String, Object>> successResponse(Object data) {
		return successResponse(data, "", HttpStatus.OK);
	}

	protected ResponseEntity<Map<String, Object>> errorResponse(Object errors, String message, HttpStatus status) {
		Map<String, Object> res = new HashMap<>();
		res.put("error", true);
		res.put("message", message);
		res.put("errors", errors);
		return ResponseEntity.status(status).body(res);
	}

	/**
	 * Extracts field-level validation errors from the binding result.
	 *
	 * @param br
	 * @return
	 */
	protected Map<String, String> fieldErrors(BindingResult br) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError e : br.getFieldErrors()) {
			errors.put(e.getField(), e.getDefaultMessage());
		}
		return errors;
	}

	public abstract BaseServiceInt<D> getService();

	/**
	 * GET → returns all entities
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> get() {
		List<D> list = getService().search();
		return successResponse(list);
	}

	/**
	 * GET /{id} → returns single entity by id
	 */
	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
		D obj = getService().findById(id);
		if (obj == null) {
			return errorResponse(null, getService().getDao().getEntityClassName() + " not found", HttpStatus.NOT_FOUND);
		}
		return successResponse(obj);
	}

	/**
	 * DELETE /{id} → deletes entity by id
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		D existing = getService().findById(id);
		if (existing == null) {
			return errorResponse(null, getService().getDao().getEntityClassName() + " not found", HttpStatus.NOT_FOUND);
		}
		getService().delete(id);
		return successResponse(null, getService().getDao().getEntityClassName() + " deleted successfully", HttpStatus.OK);
	}

}
