package com.homemadewonder.www.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Address;
import com.homemadewonder.www.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddresssController {

	@Autowired
	private AddressService addressService;

	@PutMapping("/edit/{customerId}/{addressId}")
	public ResponseEntity<Address> editAddress(@PathVariable Long customerId, @PathVariable Long addressId,
			@RequestBody Address updatedAddress) {
		Address editedAddress = addressService.editAddress(customerId, addressId, updatedAddress);
		return ResponseEntity.ok(editedAddress);
	}

}
