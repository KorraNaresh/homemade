package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByProductProductId(long productId);

	Review findByProductAndCustomer(Product product, Customer customer);

}
