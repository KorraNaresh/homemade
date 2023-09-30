package com.homemadewonder.www.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.repository.CustomerRepository;
import com.homemadewonder.www.service.CustomerService;

@Service
public class CustomerServiceimpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer saveCustomer(Customer customer) {
		try {
			return customerRepository.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Failed to save customer: " + e.getMessage());
		}
	}

	@Override
	public List<Customer> getAllCustomer() {
		try {
			return customerRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Failed to fetch all customers: " + e.getMessage());
		}
	}

	@Override
	public String deleteCustomer(long customerId) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

		if (customerOptional.isPresent()) {
			customerRepository.deleteById(customerId);
			return "Customer with ID " + customerId + " deleted successfully.";
		} else {
			return "Customer with ID " + customerId + " not found";
		}
	}

	@Override
	public Customer getCustomerById(long customerId) {
		try {
			Optional<Customer> customerOptional = customerRepository.findById(customerId);

			if (customerOptional.isPresent()) {
				return customerOptional.get();
			} else {
				throw new CustomerException("Customer with ID " + customerId + " not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Failed to fetch customer by ID: " + e.getMessage());
		}
	}

	@Override
	public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
		Customer existingCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerException("Customer not found"));

		existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
		existingCustomer.setEmail(updatedCustomer.getEmail());
		existingCustomer.setContactNo(updatedCustomer.getContactNo());
		existingCustomer.setPassword(updatedCustomer.getPassword());

		return customerRepository.save(existingCustomer);
	}

	@Override
	public Customer findByEmail(String email) {

		return customerRepository.findByEmail(email);
	}

	@Override
	public long countall() {
	
		return customerRepository.count();
	}

}
