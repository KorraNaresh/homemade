package com.homemadewonder.www.service;

import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.entity.CartItem;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.excpetion.CartItemException;
import com.homemadewonder.www.excpetion.CustomerException;

public interface CartItemService {
	
public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(long customerId, Long id,CartItem cartItem) throws CartItemException,CustomerException ;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long customerId);
	
	public void removeCartItem(Long customerId,Long cartItemId) throws CartItemException, CustomerException;
//	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	

}
