package com.homemadewonder.www.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;

	private String productName;

	private double productCost;

	private String productOffer;

	private long productQuantity;

	private String productDescription;

	@Column(name = "discounted_price")
	private double discountedPrice;

	@Column(name = "discount_percent")
	private String discountPercent;

	@Column(name = "brand")
	private String brand;

	@Column(name = "color")
	private String color;

	@Column(name = "num_ratings")
	private int numRatings;

	@Lob
	@Column(length = 500000)
	private byte[] pImage;

	private String createdAt;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "sub_id")
	private Subcategory subcategory;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Productimages> productImages = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Deal> deals = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rating> ratings = new ArrayList<>();

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductCost() {
		return productCost;
	}

	public void setProductCost(double productCost) {
		this.productCost = productCost;
	}

	public String getProductOffer() {
		return productOffer;
	}

	public void setProductOffer(String productOffer) {
		this.productOffer = productOffer;
	}

	public long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(String discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getNumRatings() {
		return numRatings;
	}

	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}

	public byte[] getpImage() {
		return pImage;
	}

	public void setpImage(byte[] pImage) {
		this.pImage = pImage;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public List<Productimages> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<Productimages> productImages) {
		this.productImages = productImages;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Deal> getDeals() {
		return deals;
	}

	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public Product(long productId, String productName, double productCost, String productOffer, long productQuantity,
			String productDescription, double discountedPrice, String discountPercent, String brand, String color,
			int numRatings, byte[] pImage, String createdAt, Subcategory subcategory, List<Productimages> productImages,
			List<Review> reviews, List<Deal> deals, List<Rating> ratings) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productCost = productCost;
		this.productOffer = productOffer;
		this.productQuantity = productQuantity;
		this.productDescription = productDescription;
		this.discountedPrice = discountedPrice;
		this.discountPercent = discountPercent;
		this.brand = brand;
		this.color = color;
		this.numRatings = numRatings;
		this.pImage = pImage;
		this.createdAt = createdAt;
		this.subcategory = subcategory;
		this.productImages = productImages;
		this.reviews = reviews;
		this.deals = deals;
		this.ratings = ratings;
	}

	public Product() {
		super();

	}

}
