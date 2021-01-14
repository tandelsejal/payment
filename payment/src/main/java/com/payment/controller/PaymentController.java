package com.payment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.Service.PaymentService;
import com.payment.model.Address;
import com.payment.model.Card;
import com.payment.model.Merchant;
import com.payment.model.User;
import com.payment.repository.CardRepository;
import com.payment.repository.MerchantRepository;

@RestController
public class PaymentController {
	@Autowired
	PaymentService paymentServ;
	
	@Autowired
	MerchantRepository merchantRepo;
	
	@PostMapping(value = "/user/payment/")
	public String doPayment(@RequestBody Merchant merchant, @RequestBody User user, @RequestBody Address addr,
			@RequestBody Card card) {
		Optional<Merchant> temp = merchantRepo.findById(merchant.getiId());
		if (temp != null) {
			Merchant merchantSystem = temp.get();
			merchantSystem = new Merchant();
			merchantSystem.setiId(merchant.getiId());
			merchantSystem.setsAccountId(merchant.getsAccountId());
			merchantSystem.setMerchantRefNum(merchant.getMerchantRefNum());
			merchantSystem.setPayment(merchant.getPayment());
			merchantSystem.setUser(merchant.getUser());
			merchantSystem = paymentServ.doPaymentHandelRequest(merchantSystem, user, addr, card);
			if (merchantSystem.doHaveValidToken(System.currentTimeMillis())) {
				return paymentServ.doPayment(merchantSystem, user);
			} else {
				return "Payment Fail..!!";
			}

		}
		return "Given Merchant is not available in System...!!";
	}
}
