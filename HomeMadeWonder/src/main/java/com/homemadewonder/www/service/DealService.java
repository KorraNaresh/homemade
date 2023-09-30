package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Deal;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.request.DealRequestDTO;

public interface DealService {
	
	void createDeal(DealRequestDTO dealRequestDTO);

	List<Product> getProductsByDealCreatedate(String createdate);
	
	List<Deal> getalldeals();
	
	long countall();
	
	
	List<Deal> getAllDealsByOfferAvailability(String offerAvailability);
	
	
	
	
	
	

}
