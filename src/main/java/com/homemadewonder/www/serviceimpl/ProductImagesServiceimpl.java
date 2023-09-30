package com.homemadewonder.www.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Productimages;
import com.homemadewonder.www.repository.ProductimageRepository;
import com.homemadewonder.www.service.ProductimagesService;

import jakarta.transaction.Transactional;


@Service
public class ProductImagesServiceimpl implements ProductimagesService {
	
	@Autowired
	private ProductimageRepository productimagesRepository;

    @Override
    public List<Productimages> getProductImagesByProductId(Long productId) {
        return productimagesRepository.findByProductProductId(productId);
    }

    @Override
    @Transactional
    public List<Productimages> saveProductImages(Long productId, List<MultipartFile> files) {
        try {
            List<Productimages> savedImages = new ArrayList<>();

            // Check the current count of images for the given productId
            List<Productimages> existingImages = productimagesRepository.findByProductProductId(productId);
            int currentImageCount = existingImages.size();
            int maxAllowedImages = 10;

            if (currentImageCount + files.size() > maxAllowedImages) {
                throw new RuntimeException("Maximum image count (" + maxAllowedImages + ") reached for productId: " + productId);
            }

            for (MultipartFile file : files) {
                Productimages productImage = new Productimages();

                // Create a Product object with the given productId
                Product product = new Product();
                product.setProductId(productId);
                productImage.setProduct(product);

                byte[] imageData = file.getBytes();
                productImage.setImageData(imageData);

        
                // Save the Productimages entity
                savedImages.add(productimagesRepository.save(productImage));
            }

            return savedImages;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save product images", e);
        }
    }


    @Override
    public void deleteProductImage(Long imageId) {
        productimagesRepository.deleteById(imageId);
    }

    @Override
    @Transactional
    public Productimages updateProductImage(Long imageId, MultipartFile file) {
        Productimages existingImage = productimagesRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Product image with ID " + imageId + " not found"));

        try {
            byte[] newImageData = file.getBytes();
            existingImage.setImageData(newImageData);
            return productimagesRepository.save(existingImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update product image", e);
        }
    }

}
