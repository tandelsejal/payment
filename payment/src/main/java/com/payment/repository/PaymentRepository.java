package com.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.payment.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

}
