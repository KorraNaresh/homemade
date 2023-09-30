package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Address;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Order;
import com.homemadewonder.www.excpetion.OrderException;

public interface OrderService {
	
	
	public Order createOrder(Customer customer, Address shippingAdress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId)throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancledOrder(Long orderId) throws OrderException;
	
	public List<Order>getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
	
	long count();
	
	Order createOrdezzzzr(Customer customer, Address addressId);

}
