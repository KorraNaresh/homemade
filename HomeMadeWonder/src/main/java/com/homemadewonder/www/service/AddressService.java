package com.homemadewonder.www.service;

import com.homemadewonder.www.entity.Address;

public interface AddressService {

	  Address editAddress(Long customerId, Long addressId, Address updatedAddress);

}
