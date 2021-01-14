package com.payment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.repository.UserReposiroty;

@Service
public class UserService {
	@Autowired
	UserReposiroty userRepo;
	
	public boolean emailExist(String sEmail) {
		if(userRepo.findBysEmail(sEmail) != null) {
			return true;
		}
		return false;
	}
	
}
