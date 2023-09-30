package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Rating;

public interface RatingRepository  extends JpaRepository<Rating, Long>{
	
	Rating findByProductAndCustomer(Product product, Customer customer);
	List<Rating> findByProductProductId(long ProductId);


}
