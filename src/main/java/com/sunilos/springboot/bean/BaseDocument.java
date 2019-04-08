package com.sunilos.springboot.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Document bean. It is persistent object. It contains information of document.
 * 
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */

@MappedSuperclass
public class BaseDocument implements Serializable {

	@Id
	@GenericGenerator(name = "hiIncrement", strategy = "increment")
	@GeneratedValue(generator = "hiIncrement")
	@Column(name = "ID", unique = true, nullable = false)
	protected Long id;

	@Column(name = "NAME", length = 100)
	protected String name = null;

	@Column(name = "TYPE", length = 100)
	protected String type = null;

	@Column(name = "DESCRIPTION", length = 500)
	protected String description = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}