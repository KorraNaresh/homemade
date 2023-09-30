package com.homemadewonder.www.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "categories")
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;

	private String categoryNames;
	
	private String categoryOffer;
	
	@Lob
	
	private byte[] categoryImage;

	  
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "main_id")
    private Categories mainCategory;
    
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Subcategory> subcategories = new ArrayList<>();

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public String getCategoryOffer() {
		return categoryOffer;
	}

	public void setCategoryOffer(String categoryOffer) {
		this.categoryOffer = categoryOffer;
	}

	public byte[] getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(byte[] categoryImage) {
		this.categoryImage = categoryImage;
	}

	public Categories getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(Categories mainCategory) {
		this.mainCategory = mainCategory;
	}

	public List<Subcategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}

	public Categories(long categoryId, String categoryNames, String categoryOffer, byte[] categoryImage,
			Categories mainCategory, List<Subcategory> subcategories) {
		super();
		this.categoryId = categoryId;
		this.categoryNames = categoryNames;
		this.categoryOffer = categoryOffer;
		this.categoryImage = categoryImage;
		this.mainCategory = mainCategory;
		this.subcategories = subcategories;
	}

	public Categories() {
		super();
	
	}
	
	
	
	

//  @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
//  private List<Product> products=new ArrayList<>();

    
   
    

}
