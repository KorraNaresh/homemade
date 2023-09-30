package com.homemadewonder.www.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Address;
import com.homemadewonder.www.entity.Customer;
import com.homemadewonder.www.excpetion.AddressNotfound;
import com.homemadewonder.www.excpetion.CustomerException;
import com.homemadewonder.www.repository.AddressRepository;
import com.homemadewonder.www.repository.CustomerRepository;
import com.homemadewonder.www.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	
	 @Autowired
	    private AddressRepository addressRepository;

	    @Autowired
	    private CustomerRepository customerRepository;

	    @Override
	    public Address editAddress(Long customerId, Long addressId, Address updatedAddress) {
	        Customer customer = customerRepository.findById(customerId)
	                .orElseThrow(() -> new CustomerException("Customer not found"));

	        Address addressToEdit = customer.getAddresses()
	                .stream()
	                .filter(address -> address.getId().equals(addressId))
	                .findFirst()
	                .orElseThrow(() -> new AddressNotfound("Address not found for the given customer"));

	      
	        addressToEdit.setFirstName(updatedAddress.getFirstName());
	        addressToEdit.setLastName(updatedAddress.getLastName());
	        addressToEdit.setStreetAddress(updatedAddress.getStreetAddress());
	        addressToEdit.setCity(updatedAddress.getCity());
	        addressToEdit.setState(updatedAddress.getState());
	        addressToEdit.setZipCode(updatedAddress.getZipCode());
	        addressToEdit.setMobile(updatedAddress.getMobile());

	        return addressRepository.save(addressToEdit);
	    }

}
