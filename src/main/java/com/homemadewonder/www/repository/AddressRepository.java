package com.homemadewonder.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
