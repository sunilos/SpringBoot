package com.sunilos.springboot.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Role bean. It is a persistent object.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Entity
@Table(name = "ST_ROLE")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	protected Long id;

	@Column(name = "NAME", length = 50, unique = true)
	protected String name;

	@Column(name = "DESCRIPTION", length = 255)
	protected String description;

	@Column(name = "CREATED_BY", length = 50)
	protected String createdBy;

	@Column(name = "MODIFIED_BY", length = 50)
	protected String modifiedBy;

	@CreatedDate
	@Column(name = "CREATED_DATETIME")
	protected Timestamp createdDatetime;

	@LastModifiedDate
	@Column(name = "MODIFIED_DATETIME")
	protected Timestamp modifiedDatetime;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getCreatedBy() { return createdBy; }
	public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

	public String getModifiedBy() { return modifiedBy; }
	public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

	public Timestamp getCreatedDatetime() { return createdDatetime; }
	public void setCreatedDatetime(Timestamp createdDatetime) { this.createdDatetime = createdDatetime; }

	public Timestamp getModifiedDatetime() { return modifiedDatetime; }
	public void setModifiedDatetime(Timestamp modifiedDatetime) { this.modifiedDatetime = modifiedDatetime; }
}
