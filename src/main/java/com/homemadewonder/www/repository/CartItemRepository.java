package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.entity.CartItem;
import com.homemadewonder.www.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.customerId=:customerId")
	public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product,
			@Param("size") String size, @Param("customerId") Long customerId);

	
	
    List<CartItem> findByCustomerId(Long customerId);

}
