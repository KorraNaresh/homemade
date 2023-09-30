package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homemadewonder.www.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "SELECT * FROM orders WHERE customer_customer_id = :customerId", nativeQuery = true)
	List<Order> findByCustomerCustomerId(@Param("customerId") Long customerId);

	
	  @Query("SELECT o.customer.customerId FROM Order o WHERE o.id = :orderId")
	    Long findCustomerIdByOrderId(@Param("orderId") Long orderId);

}
