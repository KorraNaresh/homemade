package com.homemadewonder.www.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String size;

	private int quantity;

	private double price;

	private double discountedPrice;
	
	private Long customerId;
	
	@JsonIgnore
	@ManyToOne()
	private Cart cart;

	@ManyToOne
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CartItem(Long id, String size, int quantity, double price, double discountedPrice, Long customerId,
			Cart cart, Product product) {
		super();
		this.id = id;
		this.size = size;
		this.quantity = quantity;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.customerId = customerId;
		this.cart = cart;
		this.product = product;
	}

	public CartItem() {
		super();
	
	}

	
	
	
	
}
