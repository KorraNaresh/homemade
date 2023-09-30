package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Subcategory;

public interface ProductService {

	Product saveProduct(Product product, Subcategory subId);

	List<Product> getProductById(Long id);

	List<Product> getAllProducts();

	void deleteProduct(Long productId);

	Product updateProduct(Long productId, Product updatedProduct);

	void saveProducts(List<Product> products);

	List<Product> findAllByOrderByProductCostDesc();

	List<Product> findAllByOrderByDiscountPercentDesc();
	
	 int countProductsBySubcategory(long subcategoryId);
	 
	 long countall();
	 
	 List<Product> getProductsBySubcategory(long subcategoryId);
	 

}
