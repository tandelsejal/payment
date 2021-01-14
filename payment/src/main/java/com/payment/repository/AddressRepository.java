package com.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.payment.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{

	Iterable<Address> findAllByuser(int userId);

}
