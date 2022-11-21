package com.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


//Autor: Jaime Naranjo
//última Modificación : 19-11-2022

@Entity
public class Usuario{
	
	@NotEmpty
	@Column(name = "name", length = 50)
	private String name;
	
	@NotEmpty
	@Id
	@Column(name = "username", length = 50)
	private String username;
	
	@NotEmpty
	@Email
	@Column(name = "email", length = 50)
	private String email;
	
	@NotEmpty
	@Column(name = "phone", length = 20)
	private String phone;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\", \"username\":\"" + username + "\", \"email\":\"" + email
				+ "\", \"phone\":\"" + phone + "\"}";
	}
}
