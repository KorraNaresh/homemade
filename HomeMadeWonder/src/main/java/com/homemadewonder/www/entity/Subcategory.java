package com.homemadewonder.www.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Subcategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long subId;

	private String subCategoryNames;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_category_id")
	private Categories parentCategory;

	@OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

	public String getSubCategoryNames() {
		return subCategoryNames;
	}

	public void setSubCategoryNames(String subCategoryNames) {
		this.subCategoryNames = subCategoryNames;
	}

	public Categories getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Categories parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Subcategory(long subId, String subCategoryNames, Categories parentCategory, List<Product> products) {
		super();
		this.subId = subId;
		this.subCategoryNames = subCategoryNames;
		this.parentCategory = parentCategory;
		this.products = products;
	}

	public Subcategory() {
		super();

	}

}
