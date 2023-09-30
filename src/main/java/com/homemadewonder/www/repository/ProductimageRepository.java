package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Productimages;

public interface ProductimageRepository extends JpaRepository<Productimages, Long> {

	
	 List<Productimages> findByProductProductId(Long productId);
}
