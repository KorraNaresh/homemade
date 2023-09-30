package com.homemadewonder.www.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.customeropeartions.OrderStatus;
import com.homemadewonder.www.customeropeartions.PaymentStatus;
import com.homemadewonder.www.entity.Address;
import com.homemadewonder.www.entity.Cart;
import com.homemadewonder.www.entity.CartItem;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Order;
import com.homemadewonder.www.entity.OrderItem;
import com.homemadewonder.www.excpetion.CartException;
import com.homemadewonder.www.excpetion.OrderException;
import com.homemadewonder.www.repository.AddressRepository;
import com.homemadewonder.www.repository.CartItemRepository;
import com.homemadewonder.www.repository.CartRepository;
import com.homemadewonder.www.repository.CustomerRepository;
import com.homemadewonder.www.repository.OrderItemRepository;
import com.homemadewonder.www.repository.OrderRepository;
import com.homemadewonder.www.service.CartService;
import com.homemadewonder.www.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceimpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CustomerRepository userRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	@Transactional
	public Order createOrder(Customer customer, Address shippAddress) {

		shippAddress.setCustomers(customer);
		Address address = addressRepository.save(shippAddress);
		customer.getAddresses().add(address);
		userRepository.save(customer);

		Cart cart = cartService.findCustomerCart(customer.getCustomerId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();

			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setCustomerId(item.getCustomerId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);

			orderItems.add(createdOrderItem);
		}

		Order createdOrder = new Order();
		createdOrder.setCustomer(customer);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setFinalPrice(cart.getFinalPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());

		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus(OrderStatus.PENDING);
		createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
		createdOrder.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepository.save(createdOrder);

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		removeCartItemsByCustomer(customer);

		return savedOrder;

	}
	
	
	@Override
	@Transactional
    public Order createOrdezzzzr(Customer customer, Address addressId) {
        // Find the customer's cart
        Cart cart = cartService.findCustomerCart(customer.getCustomerId());

        Address shippingAddress = customer.getAddresses().stream()
                .filter(address -> address.getId().equals(addressId.getId())) // Use .getId() to compare IDs
                .findFirst()
                .orElse(null);

      
        // Create the order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();

			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setCustomerId(item.getCustomerId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);

			orderItems.add(createdOrderItem);
		}

        // Create the order
        Order createdOrder = new Order();
        createdOrder.setCustomer(customer);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setFinalPrice(cart.getFinalPrice());
        createdOrder.setDiscounte(cart.getDiscounte());
        createdOrder.setTotalItem(cart.getTotalItem());

        // Set the shipping address
        createdOrder.setShippingAddress(shippingAddress);

        // Set other order properties
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        // Save the order
        Order savedOrder = orderRepository.save(createdOrder);

        // Associate order items with the order
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
        }

        // Clear the customer's cart
        removeCartItemsByCustomer(customer);

        return savedOrder;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	@Transactional
	private void removeCartItemsByCustomer(Customer customer) {
	    List<CartItem> cartItemsToRemove = cartItemRepository.findByCustomerId(customer.getCustomerId());
	    
	    for (CartItem cartItem : cartItemsToRemove) {
	        Cart existingCart = cartRepository.findById(cartItem.getCart().getId())
	                .orElseThrow(() -> new CartException("Cart not found"));
	        
	        existingCart.setTotalPrice(existingCart.getTotalPrice() - cartItem.getPrice());
	        existingCart.setDiscounte((int) (existingCart.getDiscounte() - (cartItem.getPrice() - cartItem.getDiscountedPrice())));
	        existingCart.setTotalItem(existingCart.getTotalItem() - cartItem.getQuantity());
	        existingCart.setFinalPrice(existingCart.getFinalPrice() - cartItem.getDiscountedPrice());

	        cartRepository.save(existingCart);
	        
	        cartItemRepository.deleteById(cartItem.getId());
	    }
	    
	    // After removing cart items, you can clean the CartItem repository
	    cartItemRepository.deleteInBatch(cartItemsToRemove);
	}


	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);

		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SHIPPED);
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.DELIVERED);
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt = orderRepository.findById(orderId);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id " + orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long customerId) {
		List<Order> orders = orderRepository.findByCustomerCustomerId(customerId);
		return orders;
	}

	@Override
	public List<Order> getAllOrders() {

		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		// Order order =findOrderById(orderId);

		orderRepository.deleteById(orderId);

	}


	@Override
	public long count() {

		return orderRepository.count();
	}

}
