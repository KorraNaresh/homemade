package com.homemadewonder.www.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Review;
import com.homemadewonder.www.service.ReviewService;


@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	Logger logger =LoggerFactory.getLogger(ReviewController.class);


	@Autowired
	private ReviewService reviewservice;

	@PostMapping("/save/{productId}")
	public ResponseEntity<Review> saveRating(@RequestBody Review review, @PathVariable Product productId) {

		Review savedReview = reviewservice.saveReviewDetails(review, productId);
		return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
	}

	@GetMapping("/get/{productId}")
	public ResponseEntity<List<Review>> getReviewsOfProduct(@PathVariable long productId) {
		List<Review> productReviews = reviewservice.getReviewByWithProductId(productId);
		return new ResponseEntity<>(productReviews, HttpStatus.OK);
	}

	@PutMapping("/update/{productId}/{customerId}")
	public ResponseEntity<Review> updateTheReview(@RequestBody Review review, @PathVariable long productId,
			@PathVariable long customerId) {

		Review savedRating = reviewservice.updateReview(review, productId, customerId);
		return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
	}

}
