package com.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.payment.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

}
