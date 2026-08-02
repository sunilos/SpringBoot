package com.sunilos.springboot.bean;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * User bean. It is a persistent object.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
@Entity
@Table(name = "ST_USER")
public class User extends BaseDTO {

	@Column(name = "LOGIN", length = 50, unique = true)
	protected String loginId;

	@Column(name = "PASSWORD", length = 255)
	protected String password;

	@Column(name = "FIRST_NAME", length = 50)
	protected String firstName;

	@Column(name = "LAST_NAME", length = 50)
	protected String lastName;

	@Column(name = "EMAIL", length = 100)
	protected String email;

	@Column(name = "MOBILE", length = 15)
	protected String mobile;

	@Column(name = "DOB")
	protected Date dob;

	@Column(name = "GENDER", length = 10)
	protected String gender;

	@Column(name = "ROLE", length = 20)
	protected String role;

	@Column(name = "STATUS")
	protected Integer status;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
