package com.homemadewonder.www.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.entity.CartItem;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.excpetion.CartException;
import com.homemadewonder.www.excpetion.CartItemException;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.repository.CartItemRepository;
import com.homemadewonder.www.repository.CartRepository;
import com.homemadewonder.www.service.CartItemService;
import com.homemadewonder.www.service.CustomerService;


@Service
public class CartitemServiceimpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private CustomerService userService;
	
	@Autowired
	private CartRepository  cartRepository;

	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		
		cartItem.setQuantity(cartItem.getQuantity());
		cartItem.setPrice(cartItem.getProduct().getProductCost()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
	}



	@Override
	public CartItem isCartItemExist(Cart cart, Product product,String size, Long customerId) {
		
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product,size, customerId);
		
		return cartItem;
	}
	

	
//	@Override
//	public CartItem updateCartItem(long customerId, Long id, CartItem cartItem) throws CartItemException, CustomerException {
//		
//		CartItem item=findCartItemById(id);
//		Customer user=userService.getCustomerById(item.getCustomerId());
//		
//		if(user.getCustomerId()==(customerId)) {
//			
//			item.setQuantity(cartItem.getQuantity());
//			item.setPrice(item.getQuantity()*item.getProduct().getProductCost());
//			item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
//			
//			return cartItemRepository.save(item);
//				
//			
//		}
//		else {
//			throw new CartItemException("You can't update  another users cart_item");
//		}
//		
//	}
	
	@Override
	public CartItem updateCartItem(long customerId, Long id, CartItem cartItem) throws CartItemException, CustomerException {
	    CartItem item = findCartItemById(id);
	    Customer user = userService.getCustomerById(item.getCustomerId());
	    Product product = item.getProduct();

	    if (user.getCustomerId() == customerId) {

	        if (cartItem.getQuantity() > product.getProductQuantity()) {
	            throw new CartItemException("Requested quantity exceeds available stock.");
	        }

	        item.setQuantity(cartItem.getQuantity());
	        item.setPrice(item.getQuantity() * product.getProductCost());
	        item.setDiscountedPrice(item.getQuantity() * product.getDiscountedPrice());

	        return cartItemRepository.save(item);
	    } else {
	        throw new CartItemException("You can't update another user's cart_item");
	    }
	}

	@Override
	public void removeCartItem(Long customerId,Long cartItemId) throws CartItemException, CustomerException {
		
			System.err.println("userId- "+customerId+" cartItemId "+cartItemId);
			
			CartItem cartItem=findCartItemById(cartItemId);
			
			Customer user=userService.getCustomerById(cartItem.getCustomerId());
			Customer reqUser=userService.getCustomerById(customerId);
			
			if(user.getCustomerId()==(reqUser.getCustomerId())) {
	
				Optional<CartItem> ci = cartItemRepository.findById(cartItem.getId());
			
		
				System.err.println(cartItem.getCart().getId());
	        Cart existingCart = cartRepository.findById(cartItem.getCart().getId()).orElseThrow(() -> new CartException("Cart not found"));

	        
	  
	        existingCart.setTotalPrice( existingCart.getTotalPrice() - ci.get().getPrice() );
	        existingCart.setDiscounte((int)(existingCart.getDiscounte() - (ci.get().getPrice() - ci.get().getDiscountedPrice())));
	        existingCart.setTotalItem(  existingCart.getTotalItem() - ci.get().getQuantity()    );
	        existingCart.setFinalPrice( existingCart.getFinalPrice() - ci.get().getDiscountedPrice() );
	     

	
	         cartRepository.save(existingCart);
			
	 		cartItemRepository.deleteById(cartItem.getId());
			
		}
		else {
			throw new CustomerException("you can't remove anothor users item");
		}
		
	}

	
	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id : "+cartItemId);
	}

}
