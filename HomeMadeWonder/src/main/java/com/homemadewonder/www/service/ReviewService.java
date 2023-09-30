package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Review;

public interface ReviewService {

	 Review saveReviewDetails(Review review, Product productId);

	 List<Review> getReviewByWithProductId(long productId);

	 Review updateReview(Review r, long productId, long customerId);

}
