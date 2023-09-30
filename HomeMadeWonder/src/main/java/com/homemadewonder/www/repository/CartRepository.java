package com.homemadewonder.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homemadewonder.www.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	
	@Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId")
	Cart findByCustomerId(@Param("customerId") long customerId);





}
