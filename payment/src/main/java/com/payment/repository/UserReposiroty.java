package com.payment.repository;

import org.springframework.data.repository.CrudRepository;

import com.payment.model.User;

public interface UserReposiroty extends CrudRepository<User, Integer>{

	User findBysEmail(String email);

}
