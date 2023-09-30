package com.homemadewonder.www.controller;

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

import com.homemadewonder.www.entity.Productimages;
import com.homemadewonder.www.service.ProductimagesService;


@RestController
@RequestMapping("/api/productimages")
public class ProductImagesController {
	
	Logger logger =LoggerFactory.getLogger(ProductImagesController.class);

	@Autowired
	private ProductimagesService productimagesService;

	@GetMapping("/byProductId/{productId}")
	public ResponseEntity<List<Productimages>> getProductImagesByProductId(@PathVariable Long productId) {
		List<Productimages> productImages = productimagesService.getProductImagesByProductId(productId);
		return new ResponseEntity<>(productImages, HttpStatus.OK);
	}
	@PostMapping("/save/{productId}")
	public ResponseEntity<List<Productimages>> saveProductImages(@PathVariable("productId") Long productId,
	        @RequestParam("files") List<MultipartFile> files) {
	    try {
	        List<Productimages> savedImages = productimagesService.saveProductImages(productId, files);
	        return new ResponseEntity<>(savedImages, HttpStatus.CREATED);
	    } catch (RuntimeException e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@DeleteMapping("/delete/{imageId}")
	public ResponseEntity<Void> deleteProductImage(@PathVariable Long imageId) {
		productimagesService.deleteProductImage(imageId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/{imageId}")
	public ResponseEntity<Productimages> updateProductImage(@PathVariable Long imageId,
			@RequestParam("file") MultipartFile file) {
		Productimages updatedImage = productimagesService.updateProductImage(imageId, file);
		return new ResponseEntity<>(updatedImage, HttpStatus.OK);
	}
}
