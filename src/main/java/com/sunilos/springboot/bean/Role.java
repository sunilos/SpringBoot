package com.sunilos.springboot.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Role bean. It is a persistent object.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Entity
@Table(name = "ST_ROLE")
public class Role extends BaseDTO {

	@Column(name = "NAME", length = 50, unique = true)
	protected String name;

	@Column(name = "DESCRIPTION", length = 255)
	protected String description;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}
