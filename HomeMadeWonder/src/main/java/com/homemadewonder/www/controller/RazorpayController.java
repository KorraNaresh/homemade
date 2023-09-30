package com.homemadewonder.www.controller;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.homemadewonder.www.customeropeartions.OrderStatus;
import com.homemadewonder.www.customeropeartions.PaymentMethod;
import com.homemadewonder.www.customeropeartions.PaymentStatus;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.entity.Order;
import com.homemadewonder.www.entity.OrderItem;
import com.homemadewonder.www.entity.PaymentDetails;
import com.homemadewonder.www.entity.Product;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.excpetion.InsufficientProductQuantityException;
import com.homemadewonder.www.excpetion.OrderException;
import com.homemadewonder.www.repository.OrderRepository;
import com.homemadewonder.www.repository.ProductRepository;
import com.homemadewonder.www.response.PaymentLinkResponse;
import com.homemadewonder.www.service.OrderService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/pay")
public class RazorpayController {

	Logger logger = LoggerFactory.getLogger(RazorpayController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;
	


	@Autowired
	private ProductRepository productRepository;

	@Value("${razorpay.api.key}")
	private String razorpayApiKey;

	@Value("${razorpay.api.secret}")
	private String razorpayApiSecret;



	@PostMapping("/payments/{orderId}/{customerId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
			@PathVariable Customer customerId) throws RazorpayException, CustomerException, OrderException {

		Order order = orderService.findOrderById(orderId);
		try {
			// Instantiate a Razorpay client with your key ID and secret
			RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecret);

			
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", order.getFinalPrice() * 100);
			paymentLinkRequest.put("currency", "INR");

			// Create a JSON object with the customer details
			JSONObject customer = new JSONObject();
			customer.put("name", order.getCustomer().getCustomerName());
			customer.put("contact", "+91" + order.getCustomer().getContactNo());
			customer.put("email", order.getCustomer().getEmail());
			paymentLinkRequest.put("customer", customer);

			// Create a JSON object with the notification settings
			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);

			// Set the reminder settings
			JSONObject reminders = new JSONObject();
			reminders.put("sms", true);
			reminders.put("email", true);

			// Set the callback URL and method
			paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
			paymentLinkRequest.put("callback_method", "get");

			// Create the payment link using the paymentLink.create() method
			PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");

			PaymentLinkResponse res = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);

			PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);

			PaymentDetails paymentDetails = new PaymentDetails();

			paymentDetails.setPaymentId(fetchedPayment.get("payment_id"));
			paymentDetails.setRazorpayPaymentLinkId(fetchedPayment.get("id"));
			paymentDetails.setRazorpayPaymentLinkReferenceId(fetchedPayment.get("reference_id"));
			paymentDetails.setRazorpayPaymentLinkStatus(fetchedPayment.get("status"));

			order.setPaymentDetails(paymentDetails);

			order.setOrderId(fetchedPayment.get("order_id"));
			orderRepository.save(order);

			System.out.println("Payment link ID: " + paymentLinkId);
			System.out.println("Payment link URL: " + paymentLinkUrl);
			System.out.println("Order Id : " + fetchedPayment.get("order_id") + fetchedPayment);

			return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.ACCEPTED);

		} catch (RazorpayException e) {

			System.out.println("Error creating payment link: " + e.getMessage());
			throw new RazorpayException(e.getMessage());
		}
	}

	@GetMapping("/payments")
	public ResponseEntity<?> redirect(@RequestParam(name = "payment_id") String paymentId,
			@RequestParam("order_id") Long orderId) throws RazorpayException, OrderException {
		RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
		Order order = orderService.findOrderById(orderId);

		try {

			Payment payment = razorpay.payments.fetch(paymentId);
			System.out.println("payment details --- " + payment + payment.get("status"));


			if (payment.get("status").equals("captured")) {
				System.out.println("payment details --- " + payment + payment.get("status"));
     
				order.getPaymentDetails().setPaymentId(paymentId);
	            order.getPaymentDetails().setRazorpayPaymentLinkStatus(payment.get("status"));
	            
	            if ("upi".equals(payment.get("method"))) {
	                order.getPaymentDetails().setPaymentMethod(PaymentMethod.UPI);
	            }
	            else if ("card".equals(payment.get("method"))) {
	                order.getPaymentDetails().setPaymentMethod(PaymentMethod.CREDIT_CARD);
	            }
	            else if ("netbanking".equals(payment.get("method"))) {
	                order.getPaymentDetails().setPaymentMethod(PaymentMethod.NET_BANKING);
	            }


	           System.out.println("payement method -->"+payment.get("method"));
				order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
				order.setOrderStatus(OrderStatus.PLACED);
                order.setOrderId(payment.get("order_id"));
                
				// order.setOrderItems(order.getOrderItems());
				System.out.println(order.getPaymentDetails().getStatus() + "payment status ");
				orderRepository.save(order);

				List<OrderItem> orderItems = order.getOrderItems();
				for (OrderItem orderItem : orderItems) {
					Product product = orderItem.getProduct();
					System.out.println(product+"  quntity");
					long orderedQuantity = orderItem.getQuantity();
					System.out.println(orderedQuantity+" from product");

					long currentQuantity = product.getProductQuantity();
					if (currentQuantity >= orderedQuantity) {
						product.setProductQuantity(currentQuantity - orderedQuantity);
					} else {

						throw new InsufficientProductQuantityException(
								"Insufficient quantity for product: " + product.getProductName());
					}

					productRepository.save(product);
				}

			}

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("errrr payment -------- ");
			new RedirectView("https://shopwithzosh.vercel.app/payment/failed");
			throw new RazorpayException(e.getMessage());
		}

	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	

}
