package com.homemadewonder.www.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalexceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AdminNotFoundException.class)
	@ResponseBody
	public ErrorResponse employeenotfound(AdminNotFoundException expection) {
		ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msg;

	}

	@ExceptionHandler(CategoryNotFoundException.class)
	@ResponseBody
	public ErrorResponse employeenotfound(CategoryNotFoundException expection) {
		ErrorResponse msga = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msga;

	}
	
	@ExceptionHandler(CustomerException.class)
	@ResponseBody
	public ErrorResponse customernotfound(CustomerException expection) {
		ErrorResponse msga = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msga;

	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseBody
	public ErrorResponse employeenotfound(ProductNotFoundException expection) {
		ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msg;

	}
	
	
	
	
	@ExceptionHandler(CartException.class)
	@ResponseBody
	public ErrorResponse cartNotfound(CartException expection) {
		ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msg;

	}
	
	@ExceptionHandler(CartItemException.class)
	@ResponseBody
	public ErrorResponse CartItemnotfound(CartItemException expection) {
		ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msg;

	}
	
	
	@ExceptionHandler(OrderException.class)
	@ResponseBody
	public ErrorResponse Ordernotfound(OrderException expection) {
		ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msg;

	}
	
	
	
	@ExceptionHandler(AddressNotfound.class)
	@ResponseBody
	public ErrorResponse Ordernotfound(AddressNotfound expection) {
		ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, expection.getMessage());

		return msg;

	}

}
