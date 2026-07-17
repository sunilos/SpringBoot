package com.sunilos.springboot.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Contains Role form elements and their declarative input validations.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public class RoleForm {

	protected long id = 0;

	@NotEmpty
	@Size(min = 2, max = 50)
	private String name;

	@Size(max = 255)
	private String description;

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}
