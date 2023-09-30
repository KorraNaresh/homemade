package com.homemadewonder.www.excpetion;

public class InsufficientProductQuantityException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientProductQuantityException(String message) {
		super(message);
	}

}
