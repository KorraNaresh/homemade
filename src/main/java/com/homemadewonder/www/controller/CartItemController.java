package com.homemadewonder.www.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.CartItem;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.excpetion.CartItemException;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.service.CartItemService;
import com.homemadewonder.www.service.CartService;
import com.homemadewonder.www.service.CustomerService;


@RestController
@RequestMapping("/api/cartitem")
public class CartItemController {
	
	Logger logger =LoggerFactory.getLogger(CartItemController.class);

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@DeleteMapping("/{cartItemId}/{customerId}")
	public ResponseEntity<?> deleteCartItemHandler(@PathVariable Long cartItemId,@PathVariable long customerId) throws CartItemException, Exception {
		
		Customer u=customerService.getCustomerById(customerId);

	 cartItemService.removeCartItem(u.getCustomerId(), cartItemId);

		return new ResponseEntity<>( HttpStatus.ACCEPTED);
	}
	
	
	

	@PutMapping("/{cartItemId}/{customerId}")
	public ResponseEntity<CartItem> updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem,
			@PathVariable long customerId) throws CartItemException, CustomerException {

		Customer u=customerService.getCustomerById(customerId);

		CartItem updatedCartItem = cartItemService.updateCartItem(u.getCustomerId(), cartItemId, cartItem);
								
	
		 cartService.updateCart(updatedCartItem.getCart().getId(), updatedCartItem.getQuantity());

		return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
	}


}
