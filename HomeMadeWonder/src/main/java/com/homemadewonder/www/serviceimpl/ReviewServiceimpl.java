package com.homemadewonder.www.serviceimpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Review;
import com.homemadewonder.www.repository.ReviewRepository;
import com.homemadewonder.www.service.CustomerService;
import com.homemadewonder.www.service.ProductService;
import com.homemadewonder.www.service.ReviewService;


@Service
public class ReviewServiceimpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Override
	public Review saveReviewDetails(Review review, Product productId) {

		review.setProduct(productId);
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getReviewByWithProductId(long productId) {
	    try {
	        System.err.println("Entering getReviewByWithProductId");

	        List<Review> list = reviewRepository.findByProductProductId(productId);

	        System.err.println("Exiting getReviewByWithProductId");

	        return list;
	    } catch (Exception e) {
	      
	        e.printStackTrace();
	        
	        return Collections.emptyList();
	    }
	}

	@Override
	public Review updateReview(Review newReview, long productId, long customerId) {
	    List<Product> productList = productService.getProductById(productId);
	    Customer customer = customerService.getCustomerById(customerId);

	    if (productList.isEmpty() || customer == null) {
	        return null;
	    } else {
	        Product product = productList.get(0);

	        Review existingReview = reviewRepository.findByProductAndCustomer(product, customer);

	        if (existingReview != null) {
	            existingReview.setReviews(newReview.getReviews());
	            return reviewRepository.save(existingReview);
	        } else {
	            return null;
	        }
	    }
	}


}
