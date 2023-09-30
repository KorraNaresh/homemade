package com.homemadewonder.www.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.entity.CartItem;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.excpetion.CartException;
import com.homemadewonder.www.excpetion.ProductNotFoundException;
import com.homemadewonder.www.repository.CartRepository;
import com.homemadewonder.www.request.AddItemRequest;
import com.homemadewonder.www.service.CartItemService;
import com.homemadewonder.www.service.CartService;
import com.homemadewonder.www.service.ProductService;


@Service
public class CartServiceimpl implements CartService {
	
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	
	@Override
	public Cart createCart(Customer customer) {
		
		Cart cart = new Cart();
		cart.setCustomer(customer);
		Cart createdCart=cartRepository.save(cart);
		return createdCart;
	}
	
	public Cart findCustomerCart(Long customerId) {
		Cart cart =	cartRepository.findByCustomerId(customerId);
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		for(CartItem cartsItem : cart.getCartItems()) {
			totalPrice+=cartsItem.getPrice();
			totalDiscountedPrice+=cartsItem.getDiscountedPrice();
			totalItem+=cartsItem.getQuantity();
		}
		
		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(cart.getCartItems().size());
		cart.setFinalPrice(totalDiscountedPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		
		return cartRepository.save(cart);
		
	}


//	
//	
//	
//	@Override
//	public String addCartItem(Long customerId, AddItemRequest req) throws ProductNotFoundException {
//	    Cart cart = cartRepository.findByCustomerId(customerId);
//	    
//	    List<Product> products = productService.getProductById(req.getProductId());
//
//	    for (Product product : products) {
//	        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.size, customerId);
//
//	        if (isPresent == null) {
//	            CartItem cartItem = new CartItem();
//	            cartItem.setProduct(product);
//	            cartItem.setCart(cart);
//	            cartItem.setQuantity(req.getQuantity());
//	            cartItem.setSize(null);
//	            cartItem.setCustomerId(customerId);
//
//	            double price = req.getQuantity() * product.getDiscountedPrice();
//	            cartItem.setPrice(price);
//
//
//
//	     
//	            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
//	            cart.getCartItems().add(createdCartItem);
//	        }
//	    }
//
//
//	    int totalPrice = 0;
//	    int totalDiscountedPrice = 0;
//	    int totalItem = 0;
//	    
//	    for (CartItem cartItem : cart.getCartItems()) {
//	        totalPrice += cartItem.getPrice();
//	        totalDiscountedPrice += cartItem.getDiscountedPrice();
//	        totalItem += cartItem.getQuantity();
//	    }
//
//	    cart.setTotalPrice(totalPrice);
//	    cart.setDiscounte(totalPrice - totalDiscountedPrice);
//	    cart.setTotalItem(totalItem);
//	    cart.setFinalPrice(totalDiscountedPrice);
//	    
//	    
//	    cartRepository.save(cart);
//
//	    return "Item(s) Added To Cart";
//	}

	
	
	
	@Override
	public String addCartItem(Long customerId, AddItemRequest req) throws ProductNotFoundException {
	    Cart cart = cartRepository.findByCustomerId(customerId);

	    List<Product> products = productService.getProductById(req.getProductId());

	    for (Product product : products) {
	        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.size, customerId);

	        if (isPresent == null) {
	   
	            if (req.getQuantity() <= product.getProductQuantity()) {
	                CartItem cartItem = new CartItem();
	                cartItem.setProduct(product);
	                cartItem.setCart(cart);
	                cartItem.setQuantity(req.getQuantity());
	                cartItem.setSize(null);
	                cartItem.setCustomerId(customerId);

	                double price = req.getQuantity() * product.getDiscountedPrice();
	                cartItem.setPrice(price);

	                CartItem createdCartItem = cartItemService.createCartItem(cartItem);
	                cart.getCartItems().add(createdCartItem);
	            } else {
	                // Handle insufficient stock here
	                return "Insufficient stock for product with ID " + req.getProductId();
	            }
	        }
	    }

	    int totalPrice = 0;
	    int totalDiscountedPrice = 0;
	    int totalItem = 0;

	    for (CartItem cartItem : cart.getCartItems()) {
	        totalPrice += cartItem.getPrice();
	        totalDiscountedPrice += cartItem.getDiscountedPrice();
	        totalItem += cartItem.getQuantity();
	    }

	    cart.setTotalPrice(totalPrice);
	    cart.setDiscounte(totalPrice - totalDiscountedPrice);
	    cart.setTotalItem(totalItem);
	    cart.setFinalPrice(totalDiscountedPrice);

	    cartRepository.save(cart);

	    return "Item(s) Added To Cart";
	}

	
	@Override
	public Cart updateCart(Long cartId, int quantity) {
	  
	    Cart existingCart = cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart not found"));

	    int totalPrice = 0;
	    int totalDiscountedPrice = 0;
	    int totalItem = 0;

	    for (CartItem cartItem : existingCart.getCartItems()) {
	        totalPrice += cartItem.getPrice();
	        totalDiscountedPrice += cartItem.getDiscountedPrice();
	        totalItem += cartItem.getQuantity();
	    }

	   
	    existingCart.setTotalPrice(totalPrice);
	    existingCart.setDiscounte(totalPrice-totalDiscountedPrice);
	    existingCart.setTotalItem(totalItem);
	    existingCart.setFinalPrice(totalDiscountedPrice);

	    
	    return cartRepository.save(existingCart);
	}
	
	
	@Override
    public int getTotalItemByCustomerId(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            return cart.getTotalItem();
        } else {
            return 0; // Return 0 if no cart is found for the customer
        }
    }



}
