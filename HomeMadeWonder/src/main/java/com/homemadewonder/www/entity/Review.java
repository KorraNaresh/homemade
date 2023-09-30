package com.homemadewonder.www.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewId;

	private String reviews;
	

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	
	@ManyToOne()
	@JoinColumn(name = "customerId")
	private Customer customer;


	public long getReviewId() {
		return reviewId;
	}


	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}


	public String getReviews() {
		return reviews;
	}


	public void setReviews(String reviews) {
		this.reviews = reviews;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Review() {
		super();
	
	}


	public Review(long reviewId, String reviews, Product product, Customer customer) {
		super();
		this.reviewId = reviewId;
		this.reviews = reviews;
		this.product = product;
		this.customer = customer;
	}
	
	
	
	


}
