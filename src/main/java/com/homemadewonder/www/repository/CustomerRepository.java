package com.homemadewonder.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findByEmail(String email);

}
