package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Deal;

public interface DealRepositiory extends JpaRepository<Deal, Long>{
	
	   List<Deal> findAllByCreatedate(String createdate);
	   
	   List<Deal> findByOfferAvailability(String offerAvailability);

}
