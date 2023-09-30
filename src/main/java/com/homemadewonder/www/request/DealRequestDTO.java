package com.homemadewonder.www.request;

import java.time.LocalDate;

public class DealRequestDTO {

	private long productId;
	private double newProductCost;
	private String newDiscountPercent;
	
	private LocalDate offerEndDate;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getNewProductCost() {
		return newProductCost;
	}

	public void setNewProductCost(double newProductCost) {
		this.newProductCost = newProductCost;
	}

	public String getNewDiscountPercent() {
		return newDiscountPercent;
	}

	public void setNewDiscountPercent(String newDiscountPercent) {
		this.newDiscountPercent = newDiscountPercent;
	}

	public LocalDate getOfferEndDate() {
		return offerEndDate;
	}

	public void setOfferEndDate(LocalDate offerEndDate) {
		this.offerEndDate = offerEndDate;
	}

	public DealRequestDTO(long productId, double newProductCost, String newDiscountPercent, LocalDate offerEndDate) {
		super();
		this.productId = productId;
		this.newProductCost = newProductCost;
		this.newDiscountPercent = newDiscountPercent;
		this.offerEndDate = offerEndDate;
	}

	public DealRequestDTO() {
		super();
	
	}

	
}
