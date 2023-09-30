package com.homemadewonder.www.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.OrderItem;
import com.homemadewonder.www.repository.OrderItemRepository;
import com.homemadewonder.www.service.OrderItemService;


@Service
public class OrderItemServiceimpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}

}
