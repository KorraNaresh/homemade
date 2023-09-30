package com.homemadewonder.www.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Deal;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.request.DealRequestDTO;
import com.homemadewonder.www.service.DealService;



@RestController
@RequestMapping("/api/deal")
public class DealController {

	@Autowired
	private DealService dealService;

	@PostMapping("/create")
	public ResponseEntity<String> createDeals(@RequestParam List<Long> productIds, 
	        @RequestParam double newProductCost, 
	        @RequestParam String newDiscountPercent, 
	        @RequestParam LocalDate offerEndDate) {
	    try {
	        for (Long productId : productIds) {
	            DealRequestDTO dealRequestDTO = new DealRequestDTO();
	            dealRequestDTO.setProductId(productId);
	            dealRequestDTO.setNewProductCost(newProductCost);
	            dealRequestDTO.setNewDiscountPercent(newDiscountPercent);
	            dealRequestDTO.setOfferEndDate(offerEndDate); 

	            dealService.createDeal(dealRequestDTO);
	        }
	        return ResponseEntity.ok("Deals created successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating deals");
	    }
	}


	@GetMapping("/productsByCreatedate/{createdate}")
	public List<Product> getProductsByDealCreatedate(@PathVariable String createdate) {
		List<Product> products = dealService.getProductsByDealCreatedate(createdate);
		return products;
	}

	@GetMapping("/getalldeals")
	public List<Deal> getallDeals() {

		List<Deal> deals = dealService.getalldeals();

		return deals;

	}
	
	
	@GetMapping("/countall")
    public long countAllCategories() {
        return dealService.countall();
    }
	
	
	
	
	@GetMapping("/deals")
	public List<Deal> getDealsByOfferAvailability(@RequestParam(name = "offerAvailability") String offerAvailability) {
		return dealService.getAllDealsByOfferAvailability(offerAvailability);
	}
	
	
	
	

}
