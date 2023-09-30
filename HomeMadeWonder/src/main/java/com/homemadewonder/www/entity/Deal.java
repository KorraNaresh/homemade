package com.homemadewonder.www.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Deal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "createdate")
	private String createdate;
	
    @Column(name = "offer_end_date")
    private LocalDate offerEndDate;

    @Column(name = "offer_availability")
    private String offerAvailability;

	public long getdId() {
		return dId;
	}

	public void setdId(long dId) {
		this.dId = dId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public LocalDate getOfferEndDate() {
		return offerEndDate;
	}

	public void setOfferEndDate(LocalDate offerEndDate) {
		this.offerEndDate = offerEndDate;
	}

	public String getOfferAvailability() {
		return offerAvailability;
	}

	public void setOfferAvailability(String offerAvailability) {
		this.offerAvailability = offerAvailability;
	}

	public Deal(long dId, Product product, String createdate, LocalDate offerEndDate, String offerAvailability) {
		super();
		this.dId = dId;
		this.product = product;
		this.createdate = createdate;
		this.offerEndDate = offerEndDate;
		this.offerAvailability = offerAvailability;
	}

	public Deal() {
		super();
	
	}

    
    

	 
	
	
	

}
