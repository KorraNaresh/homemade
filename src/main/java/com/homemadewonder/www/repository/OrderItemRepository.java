package com.homemadewonder.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Long>{

}
