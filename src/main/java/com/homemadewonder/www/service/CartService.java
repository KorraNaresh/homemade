package com.homemadewonder.www.service;

import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.excpetion.ProductNotFoundException;
import com.homemadewonder.www.request.AddItemRequest;

public interface CartService {

	public Cart createCart(Customer customer);

	public String addCartItem(Long customerId, AddItemRequest req) throws ProductNotFoundException;

	public Cart findCustomerCart(Long customerId);
	
	Cart updateCart(Long cartId, int Quntity);
	
	int getTotalItemByCustomerId(Long customerId);

}
