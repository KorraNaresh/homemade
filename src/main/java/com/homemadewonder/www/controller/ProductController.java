package com.homemadewonder.www.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Subcategory;
import com.homemadewonder.www.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@PostMapping("/save/{subId}")
	public ResponseEntity<Product> saveProduct(@RequestParam String productName, @RequestParam double productCost,
			@RequestParam String productOffer, @RequestParam long productQuantity,
			@RequestParam String productDescription, @RequestParam String discountPercent,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "numRatings", required = false) Integer numRatings,
			@RequestParam  MultipartFile pImage, @PathVariable Subcategory subId) throws IOException {

		Product product = new Product();
		product.setProductName(productName);
		product.setProductCost(productCost);
		product.setProductOffer(productOffer);
		product.setProductQuantity(productQuantity);
		product.setProductDescription(productDescription);
		product.setDiscountPercent(discountPercent);

		discountPercent = discountPercent.replace("%", "");

		try {

			double discount = Double.parseDouble(discountPercent);

			if (discount >= 0 && discount <= 100) {

				double discountedPrice = productCost * (1 - (discount / 100.0));
				product.setDiscountedPrice(discountedPrice);
			} else {

			}
		} catch (NumberFormatException e) {

		}

		product.setBrand(brand);
		product.setColor(color);
		product.setNumRatings(0);
		if (pImage != null) {
			product.setpImage(pImage.getBytes());
		}
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mma");
		String formattedDate = dateFormat.format(currentTimestamp);

		product.setCreatedAt(formattedDate);

		Product savedProduct = productService.saveProduct(product, subId);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	@GetMapping("/getproduct/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		List<Product> products = productService.getProductById(id);

		if (!products.isEmpty()) {
			return new ResponseEntity<>(products.get(0), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getallproducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();

		Collections.sort(products, Comparator.comparingLong(Product::getProductId).reversed());
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@DeleteMapping("/deleteproduct/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/updateproduct/{productId}") 
	 public ResponseEntity<Product> updateProduct(@PathVariable Long productId, 
	   @RequestParam(value = "productName") String productName, 
	   @RequestParam(name = "productCost") double productCost, 
	   @RequestParam(name = "productOffer") String productOffer, 
	   @RequestParam(name = "productQuantity") long productQuantity, 
	   @RequestParam(name = "productDescription") String productDescription, 
	   @RequestParam  MultipartFile pImage, 
	   @RequestParam(name = "discountPercent", required = false) String discountPercent) throws IOException { 
	  Product updatedProduct = new Product(); 
	  updatedProduct.setProductName(productName); 
	  updatedProduct.setProductCost(productCost); 
	  updatedProduct.setProductOffer(productOffer); 
	  updatedProduct.setProductQuantity(productQuantity); 
	  updatedProduct.setProductDescription(productDescription); 
	  updatedProduct.setpImage(pImage.getBytes());
	  updatedProduct.setDiscountPercent(discountPercent); 
	 
	  discountPercent = discountPercent.replace("%", ""); 
	 
	  try { 
	 
	   double discount = Double.parseDouble(discountPercent); 
	 
	   if (discount >= 0 && discount <= 100) { 
	 
	    double discountedPrice = productCost * (1 - (discount / 100.0)); 
	    updatedProduct.setDiscountedPrice(discountedPrice); 
	   } else { 
	 
	   } 
	  } catch (NumberFormatException e) { 
	 
	  } 
	 
	  Product updated = productService.updateProduct(productId, updatedProduct); 
	  return ResponseEntity.ok(updated); 
	 }

	@GetMapping("/byProductCostDesc")
	public List<Product> getAllByProductCostDesc() {
		return productService.findAllByOrderByProductCostDesc();
	}

	@GetMapping("/byDiscountPercentDesc")
	public List<Product> getAllByDiscountPercentDesc() {
		return productService.findAllByOrderByDiscountPercentDesc();
	}

	@GetMapping("/count/{subcategoryId}")
	public ResponseEntity<Integer> countProductsBySubcategory(@PathVariable long subcategoryId) {
		int productCount = productService.countProductsBySubcategory(subcategoryId);
		return ResponseEntity.ok(productCount);
	}

	@GetMapping("/countall")
	public long countAllProducts() {
		return productService.countall();
	}

	@GetMapping("/getproductsbysubcategory/{subId}")
	public ResponseEntity<List<Product>> getProductsBySubcategory(@PathVariable long subId) {
		List<Product> products = productService.getProductsBySubcategory(subId);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

}