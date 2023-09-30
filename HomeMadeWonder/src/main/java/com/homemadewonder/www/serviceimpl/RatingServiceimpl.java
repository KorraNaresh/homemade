package com.homemadewonder.www.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.entity.Rating;
import com.homemadewonder.www.repository.RatingRepository;
import com.homemadewonder.www.service.CustomerService;
import com.homemadewonder.www.service.ProductService;
import com.homemadewonder.www.service.RatingService;


@Service
public class RatingServiceimpl implements RatingService {

	

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Override
	public Rating saveRatingWithProductId(Rating rating, long productId) {
		List<Product> productList = productService.getProductById(productId);

		if (productList.isEmpty()) {
			return null;
		} else {
			Product product = productList.get(0);
			rating.setProduct(product);
			rating.setCreatedAt(LocalDateTime.now());
			return ratingRepository.save(rating);
		}
	}

	@Override
	public List<Rating> getRatingByWithProductId(long productId) {

		return  ratingRepository.findByProductProductId(productId);
	}

	@Override
	public List<Rating> getAllRating() {

		return null;
	}

	@Override
	public void deleteRating(Long id) {

	}

	@Override
	public Rating updateRating(Rating newRating, long productId, long customerId) {
		List<Product> productList = productService.getProductById(productId);
		Customer customer = customerService.getCustomerById(customerId);

		if (productList.isEmpty() || customer == null) {
			return null;
		} else {
			Product product = productList.get(0);

			Rating existingRating = ratingRepository.findByProductAndCustomer(product, customer);

			if (existingRating != null) {

				existingRating.setRating(newRating.getRating());
				existingRating.setCreatedAt(LocalDateTime.now());

				return ratingRepository.save(existingRating);
			} else {

				return null;
			}
		}
	}

}
