package com.homemadewonder.www.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Productimages;

public interface ProductimagesService {
	List<Productimages> getProductImagesByProductId(Long productId);

	List<Productimages> saveProductImages(Long productId, List<MultipartFile> files);


	void deleteProductImage(Long imageId);

	Productimages updateProductImage(Long imageId, MultipartFile file);

	
}
