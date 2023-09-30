package com.homemadewonder.www.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Address;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Order;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.excpetion.OrderException;
import com.homemadewonder.www.service.CustomerService;
import com.homemadewonder.www.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	Logger logger =LoggerFactory.getLogger(OrderController.class);
	
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/save/{customerId}")
	public ResponseEntity<Order> createOrderHandler(@RequestBody Address spippingAddress,
			 @PathVariable Customer customerId) throws CustomerException{
		
		Customer s=customerService.getCustomerById(customerId.getCustomerId());
		Order order =orderService.createOrder(s, spippingAddress);
		
		return new ResponseEntity<Order>(order,HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{customerId}")
	public ResponseEntity< List<Order>> usersOrderHistoryHandler(@PathVariable Customer customerId) throws OrderException, CustomerException{
		
		Customer s=customerService.getCustomerById(customerId.getCustomerId());
		List<Order> orders=orderService.usersOrderHistory(s.getCustomerId());
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{orderId}/{customerId}")
	public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId,@PathVariable Customer customerId ) throws OrderException, CustomerException{
		
	
		Order orders=orderService.findOrderById(orderId);
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	 @GetMapping("/count")
	    public long countOrders() {
	        return orderService.count();
	    }
	 
	 
	 
	 
	 
	 @PostMapping("/create/{customerId}/{addressId}")
		public ResponseEntity<Order> createOrder(
				@PathVariable Long customerId,
				@PathVariable Address addressId) {
		    try {
		        // Retrieve the customer and address based on the provided IDs
		        Customer customer = customerService.getCustomerById(customerId);
		        
		    

		        // Call the order service to create the order
		        Order createdOrder = orderService.createOrdezzzzr(customer, addressId);

		        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
		    } catch (CustomerException e) {
		        // Handle the exception and return a 404 Not Found response
		        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		        // Handle the exception and return a 404 Not Found response for the address
		        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		    }
		}
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
