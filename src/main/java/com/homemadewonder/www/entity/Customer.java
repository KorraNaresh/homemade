package com.homemadewonder.www.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homemadewonder.www.customeropeartions.Rolesenum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;

	private String customerName;

	private String email;

	private long contactNo;

	private String password;

	private Rolesenum role;

	private String otp;
	private LocalDateTime otpExpiryTime;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rating> ratings = new ArrayList<>();


	@OneToMany(mappedBy = "customers", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@Embedded
	@ElementCollection
	@CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name = "customer_Id"))
	private List<PaymentInformation> paymentInformation = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rolesenum getRole() {
		return role;
	}

	public void setRole(Rolesenum role) {
		this.role = role;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtpExpiryTime() {
		return otpExpiryTime;
	}

	public void setOtpExpiryTime(LocalDateTime otpExpiryTime) {
		this.otpExpiryTime = otpExpiryTime;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<PaymentInformation> getPaymentInformation() {
		return paymentInformation;
	}

	public void setPaymentInformation(List<PaymentInformation> paymentInformation) {
		this.paymentInformation = paymentInformation;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Customer(long customerId, String customerName, String email, long contactNo, String password, Rolesenum role,
			String otp, LocalDateTime otpExpiryTime, List<Rating> ratings, List<Address> addresses,
			List<PaymentInformation> paymentInformation, List<Order> orders) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
		this.contactNo = contactNo;
		this.password = password;
		this.role = role;
		this.otp = otp;
		this.otpExpiryTime = otpExpiryTime;
		this.ratings = ratings;
		this.addresses = addresses;
		this.paymentInformation = paymentInformation;
		this.orders = orders;
	}

	public Customer() {
		super();

	}

	
}
