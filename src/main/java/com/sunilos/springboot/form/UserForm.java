package com.sunilos.springboot.form;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Contains User form elements and their declarative input validations.
 *
 * @author Sunil Sahu
 * @Copyright (c) SunilOS Infotech Pvt Ltd
 */
public class UserForm {

	protected long id = 0;

	@NotEmpty
	@Size(min = 3, max = 20)
	private String loginId;

	@NotEmpty
	@Size(min = 6, max = 255)
	private String password;

	@NotEmpty
	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(max = 100)
	private String email;

	@Size(max = 15)
	private String mobile;

	private Date dob;

	@Size(max = 10)
	private String gender;

	@Size(max = 20)
	private String role;

	private Integer status;

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getLoginId() { return loginId; }
	public void setLoginId(String loginId) { this.loginId = loginId; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }

	public Date getDob() { return dob; }
	public void setDob(Date dob) { this.dob = dob; }

	public String getGender() { return gender; }
	public void setGender(String gender) { this.gender = gender; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }

	public Integer getStatus() { return status; }
	public void setStatus(Integer status) { this.status = status; }
}
