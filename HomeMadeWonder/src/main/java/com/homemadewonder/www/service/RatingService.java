package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Rating;

public interface RatingService {
	
	Rating saveRatingWithProductId(Rating rating,long productId);

	List<Rating> getRatingByWithProductId(long id);

	List<Rating> getAllRating();

	void deleteRating(Long id);
	
	Rating updateRating(Rating rating,long productId,long customerId);

	


}
