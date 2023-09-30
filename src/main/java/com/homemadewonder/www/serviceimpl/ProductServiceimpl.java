package com.homemadewonder.www.serviceimpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Subcategory;
import com.homemadewonder.www.excpetion.ProductNotFoundException;
import com.homemadewonder.www.repository.ProductRepository;
import com.homemadewonder.www.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceimpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product, Subcategory subId) {
		product.setSubcategory(subId);
		return productRepository.save(product);
	}

	@Override
	public List<Product> getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
		return Collections.singletonList(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepository.deleteById(productId);
	}

	@Override
	public Product updateProduct(Long productId, Product updatedProduct) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

		existingProduct.setProductName(updatedProduct.getProductName());
		existingProduct.setProductCost(updatedProduct.getProductCost());
		existingProduct.setProductOffer(updatedProduct.getProductOffer());
		existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
		existingProduct.setProductDescription(updatedProduct.getProductDescription());
		existingProduct.setDiscountedPrice(updatedProduct.getDiscountedPrice());
		existingProduct.setDiscountPercent(updatedProduct.getDiscountPercent());
		existingProduct.setBrand(updatedProduct.getBrand());
		existingProduct.setColor(updatedProduct.getColor());
		existingProduct.setNumRatings(updatedProduct.getNumRatings());
		existingProduct.setpImage(updatedProduct.getpImage());
		return productRepository.save(existingProduct);
	}

	@Transactional
	public void saveProducts(List<Product> products) {
		productRepository.saveAll(products);
	}

	@Override
	public List<Product> findAllByOrderByProductCostDesc() {
		return productRepository.findByOrderByProductCostDesc();
	}

	@Override
	public List<Product> findAllByOrderByDiscountPercentDesc() {
		return productRepository.findByOrderByDiscountPercentDesc();
	}

	@Override
	public int countProductsBySubcategory(long subcategoryId) {
		return productRepository.countProductsBySubcategoryId(subcategoryId);
	}

	@Override
	public long countall() {
	
		return productRepository.count();
	}
	
	
	
	@Override 
    public List<Product> getProductsBySubcategory(long subcategoryId) { 
        return productRepository.findBySubcategorySubId(subcategoryId); 
    }

}
