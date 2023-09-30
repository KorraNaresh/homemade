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

import com.homemadewonder.www.entity.Rating;
import com.homemadewonder.www.service.RatingService;


@RestController
@RequestMapping("/api/rating")
public class RatingController {
	
	Logger logger =LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

	@PostMapping("/save/{productId}")
	public ResponseEntity<Rating> saveRating(@RequestBody Rating rating, @PathVariable long productId) {

		Rating savedRating = ratingService.saveRatingWithProductId(rating, productId);
		return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
	}

	@PutMapping("/update/{productId}/{customerId}")
	public ResponseEntity<Rating> updateRating(@RequestBody Rating rating, @PathVariable long productId,
			@PathVariable long customerId) {

		Rating savedRating = ratingService.updateRating(rating, productId, customerId);
		return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
	}

	@GetMapping("/get/{productId}")
	public ResponseEntity<List<Rating>> getRatingByProductId(@PathVariable long productId) {
		System.err.print(productId);
		List<Rating> savedRatings = ratingService.getRatingByWithProductId(productId);
		return new ResponseEntity<>(savedRatings, HttpStatus.OK);
	}
	
	
	

}
