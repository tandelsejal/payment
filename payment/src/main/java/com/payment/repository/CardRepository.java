package com.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.payment.model.Address;
import com.payment.model.Card;

public interface CardRepository extends CrudRepository<Card, Integer>{

	Iterable<Address> findAllByuser(int userId);

}
