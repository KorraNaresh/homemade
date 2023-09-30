package com.homemadewonder.www.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Order;
import com.homemadewonder.www.excpetion.OrderException;
import com.homemadewonder.www.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler(){
		List<Order> orders=orderService.getAllOrders();
		
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order=orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order=orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order=orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> canceledOrderHandler(@PathVariable Long orderId) throws OrderException{
		Order order=orderService.cancledOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<?> deleteOrderHandler(@PathVariable Long orderId) throws OrderException{
		orderService.deleteOrder(orderId);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
