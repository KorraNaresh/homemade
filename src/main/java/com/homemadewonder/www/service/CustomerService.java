package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Customer;

public interface CustomerService {

	Customer saveCustomer(Customer customer);

	List<Customer> getAllCustomer();

	String deleteCustomer(long customerId) throws Exception;

	Customer getCustomerById(long customerId);
	
	Customer updateCustomer(Long customerId, Customer updatedCustomer);

	Customer findByEmail(String email);
	
	long countall();

}
