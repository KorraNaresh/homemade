package com.homemadewonder.www.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productImages")
public class Productimages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private long imageId;

	@Lob
	@Column(name = "image_data",length=50000)
	private byte[] imageData;

    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Productimages(long imageId, byte[] imageData, Product product) {
		super();
		this.imageId = imageId;
		this.imageData = imageData;
		this.product = product;
	}

	public Productimages() {
		super();

	}
    
    
    
    
    
    

}
