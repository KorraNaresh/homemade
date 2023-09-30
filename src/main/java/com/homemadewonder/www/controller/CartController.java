package com.homemadewonder.www.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.excpetion.ProductNotFoundException;
import com.homemadewonder.www.request.AddItemRequest;
import com.homemadewonder.www.service.CartService;


@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	Logger logger =LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;
	

	@GetMapping("/{customerId}")
	public ResponseEntity<Cart> findUserCartHandler(@PathVariable long customerId) throws CustomerException {

		Cart cart = cartService.findCustomerCart(customerId);

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/add/{customerId}")
	public ResponseEntity<?> addItemToCart(@PathVariable long customerId, @RequestBody AddItemRequest req)
			throws CustomerException, ProductNotFoundException {

		Cart cart = cartService.findCustomerCart(customerId);

		String s = cartService.addCartItem(cart.getCustomer().getCustomerId(), req);

		return new ResponseEntity<>(s, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/totalItem/{customerId}")
	public ResponseEntity<Integer> getTotalItemByCustomerId(@PathVariable Long customerId) {
		int totalItem = cartService.getTotalItemByCustomerId(customerId);
		return ResponseEntity.ok(totalItem);
	}
	
	
	

}
