package com.homemadewonder.www.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.customeropeartions.Rolesenum;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.service.CartService;
import com.homemadewonder.www.service.CustomerService;
import com.homemadewonder.www.serviceimpl.EmailService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartService cartService;
	@Autowired
	private EmailService emailService;

	@PostMapping("/save")
	public ResponseEntity<Customer> saveCustomerData(@RequestParam String customerName,

			@RequestParam String email, @RequestParam long contactNo, @RequestParam String password) {
		Customer customer = new Customer();

		customer.setCustomerName(customerName);
		customer.setEmail(email);
		customer.setContactNo(contactNo);
		customer.setPassword(password);

		customer.setRole(Rolesenum.ROLE_USER);

		Customer savedCustomer = customerService.saveCustomer(customer);
		cartService.createCart(savedCustomer);

		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<Customer>> getAll() {
		List<Customer> customers = customerService.getAllCustomer();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@GetMapping("/getNewCustomers")
	public ResponseEntity<List<Customer>> getAllCustomersNew() {
		List<Customer> customers = customerService.getAllCustomer();
		Collections.sort(customers, Comparator.comparingLong(Customer::getCustomerId).reversed());
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	
	

	@DeleteMapping("/deleteCustomer/{customerId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable long customerId) throws Exception {
		String deletionResult = customerService.deleteCustomer(customerId);
		HttpStatus status = deletionResult.contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		return new ResponseEntity<>(deletionResult, status);
	}

	@GetMapping("/getCustomer/{customerId}")
	public ResponseEntity<?> getOneCustomer(@PathVariable long customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PutMapping("/updatecustomer/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId,
			@RequestParam(name = "customerName") String customerName, @RequestParam(name = "email") String email,
			@RequestParam(name = "contactNo") long contactNo, @RequestParam(name = "password") String password) {
		Customer updatedCustomer = new Customer();
		updatedCustomer.setCustomerName(customerName);
		updatedCustomer.setEmail(email);
		updatedCustomer.setContactNo(contactNo);
		updatedCustomer.setPassword(password);

		Customer updated = customerService.updateCustomer(customerId, updatedCustomer);
		return ResponseEntity.ok(updated);
	}

	@PostMapping("/login")
	public ResponseEntity<Customer> login(@RequestParam String email, @RequestParam String password) {
	    Customer customer = customerService.findByEmail(email);

	    if (customer != null && password.equals(customer.getPassword())) {
	        return ResponseEntity.ok(customer);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
	}

	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOTPByEmail(@RequestParam String email) {

		Customer customer = customerService.findByEmail(email);

		if (customer != null) {

			String otp = generateOTP();
			LocalDateTime otpExpiryTime = LocalDateTime.now().plusHours(1); // 1-hour expiration time
			customer.setOtp(otp);
			customer.setOtpExpiryTime(otpExpiryTime);

			customerService.saveCustomer(customer);

			sendOTPByEmail(customer.getEmail(), otp);

			return new ResponseEntity<>("OTP sent successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Customer not found.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String otp,
			@RequestParam String newPassword) {

		Customer customer = customerService.findByEmail(email);

		if (customer != null && customer.getOtp() != null && customer.getOtp().equals(otp)
				&& customer.getOtpExpiryTime() != null && LocalDateTime.now().isBefore(customer.getOtpExpiryTime())) {

			customer.setPassword(newPassword);
			customerService.saveCustomer(customer);

			customer.setOtp(null);
			customer.setOtpExpiryTime(null);
			customerService.saveCustomer(customer);

			return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid OTP or OTP has expired.", HttpStatus.BAD_REQUEST);
		}
	}

	private String generateOTP() {

		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}

	private void sendOTPByEmail(String emailAddress, String otp) {

		emailService.sendEmail(emailAddress, "Password Reset OTP", "Your OTP is: " + otp);
	}
	
	
//	@RequestMapping("/user")
//    public Customer getUserDetailsAfterLogin(Authentication authentication) {
//        List<Customer> customers = customerService.findByEmail(authentication.getName());
//        if (customers.size() > 0) {
//            return customers.get(0);
//        } else {
//            return null;
//        }
//
//    }

	 @GetMapping("/countall")
	    public long countAllCustomers() {
	        return customerService.countall();
	    }
	
	
}
