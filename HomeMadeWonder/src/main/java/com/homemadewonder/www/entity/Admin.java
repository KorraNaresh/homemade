package com.homemadewonder.www.entity;

import com.homemadewonder.www.customeropeartions.Rolesenum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(name="admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long adminId;
	
	private String adminName;
	
	private long contact;
	
	private String address;
	
	private String password;
	
	private String websiteUrl;
	
	@Column(name = "privacyAndPolices", columnDefinition = "TEXT")
	private String privacyAndPolices;
	
	@Column(name = "aboutUs", columnDefinition = "TEXT")
	private String aboutUs;
	
	@Enumerated(EnumType.STRING)
	private Rolesenum role;
	
	@Lob
	private byte[] file;

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getPrivacyAndPolices() {
		return privacyAndPolices;
	}

	public void setPrivacyAndPolices(String privacyAndPolices) {
		this.privacyAndPolices = privacyAndPolices;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public Rolesenum getRole() {
		return role;
	}

	public void setRole(Rolesenum role) {
		this.role = role;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Admin(long adminId, String adminName, long contact, String address, String password, String websiteUrl,
			String privacyAndPolices, String aboutUs, Rolesenum role, byte[] file) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.contact = contact;
		this.address = address;
		this.password = password;
		this.websiteUrl = websiteUrl;
		this.privacyAndPolices = privacyAndPolices;
		this.aboutUs = aboutUs;
		this.role = role;
		this.file = file;
	}

	public Admin() {
		super();

	}

	
	

}
