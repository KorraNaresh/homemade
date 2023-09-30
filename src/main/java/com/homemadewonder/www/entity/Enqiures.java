package com.homemadewonder.www.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Enqiures {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eId;

	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	@Column(name = "message", columnDefinition = "TEXT")
	private String message;

	public long geteId() {
		return eId;
	}

	public void seteId(long eId) {
		this.eId = eId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Enqiures(long eId, String name, String email, String phoneNumber, String message) {
		super();
		this.eId = eId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public Enqiures() {
		super();

	}
	
	
	
	
	

}
