package com.homemadewonder.www.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "total_item")
	private int totalItem;

	private double finalPrice;

	private double discounte;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "cart_items")
	private List<CartItem> cartItems = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public double getDiscounte() {
		return discounte;
	}

	public void setDiscounte(double discounte) {
		this.discounte = discounte;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Cart(Long id, double totalPrice, int totalItem, double finalPrice, double discounte, Customer customer,
			List<CartItem> cartItems) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.totalItem = totalItem;
		this.finalPrice = finalPrice;
		this.discounte = discounte;
		this.customer = customer;
		this.cartItems = cartItems;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}


	 
	

}
