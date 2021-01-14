package com.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.payment.model.Merchant;

public interface MerchantRepository extends CrudRepository<Merchant, Integer>{

}
