package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment.Exception.UserException;
import com.payment.Service.UserService;
import com.payment.model.Address;
import com.payment.model.Card;
import com.payment.model.Merchant;
import com.payment.model.User;
import com.payment.repository.AddressRepository;
import com.payment.repository.CardRepository;
import com.payment.repository.MerchantRepository;
import com.payment.repository.UserReposiroty;

@Controller
public class UserController {

	@Autowired
	UserReposiroty userRepo;
	
	@Autowired
	MerchantRepository merchantRepo;

	@Autowired
	UserService userSer;
	
	@Autowired
	CardRepository cardRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	@GetMapping("/registration")
	public String registrationForm(Model model) {
		model.addAttribute("registration", new User());
		return "registration";
	}
	
	@PostMapping(value = "/registration")
	public String registerUser(@ModelAttribute User user, @ModelAttribute Address address, Model model) throws UserException {
		model.addAttribute("registration", new User());
		if (userSer.emailExist(user.getsEmail())) {
			throw new UserException("This Email is already available in the system..." + user.getsEmail());
		} else {
			User temp = new User();
			temp.setsName(user.getsName());
			temp.setsEmail(user.getsEmail());
			temp.setsPhone(user.getsPhone());
			temp.addAddress(address);
			temp.setUserType(user.getUserType());
			temp.setsPassword(user.getsPassword());
			userRepo.save(temp);
			return "login";
		}
	}

	@PostMapping(value = "/merchant/register/")
	public Merchant registerMerchant(@RequestBody Merchant merchant) throws UserException {
		if (userSer.emailExist(merchant.getUser().getsEmail())) {
			throw new UserException("This Email is already available in the system..." + merchant.getUser().getsEmail());
		} else {
			Merchant temp = new Merchant();
			temp.setMerchantRefNum(merchant.getMerchantRefNum());
			temp.setsAccountId(merchant.getsAccountId());
			temp.setUser(merchant.getUser());
			temp.setPayment(merchant.getPayment());
			return merchantRepo.save(temp);
		}
	}
	
	@GetMapping(value = "/user/login/")
	public User getUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password)
			throws UserException {
		User user = userRepo.findBysEmail(email);
		if (user.getsPassword().equals(password)) {
			return user;
		} else {
			throw new UserException("Email and password are not matching for user :" + user.getsName());
		}
	}
	
	@PostMapping(value = "/user/addCard/")
	public User addCard(@RequestBody Card card, @RequestBody User user) {
		Card temp = new Card();
		temp.setCardNum(card.getCardNum());
		temp.setCardType( card.getCardType() );
		temp.setCvv( card.getCvv() );
		temp.setExpiryMonth(card.getExpiryMonth());
		temp.setExpiryYear(card.getExpiryYear());
		temp.setName( card.getName() );
		cardRepo.save(temp);
		
		user.addCard(temp);
		return userRepo.save(user);
	}
	
	@PostMapping(value = "/user/addAddress/")
	public User addCard(@RequestBody Address address, @RequestBody User user) {
		Address temp = new Address();
		temp.setCity(address.getCity());
		temp.setCountryCode(address.getCountryCode());
		temp.setStateCode(address.getStateCode());
		temp.setStreet(address.getStreet());
		temp.setZip(address.getZip());
		addressRepo.save(temp);
		
		user.addAddress(temp);
		return userRepo.save(user);
	}
	
	@GetMapping(value="/user/merchants/")
	public  Iterable<Merchant> getAllMerchant(){
		return merchantRepo.findAll();
	}
	
	@GetMapping(value="/user/addresses/")
	public  Iterable<Address> getAllAddress(@RequestParam(name = "id") int userId){
		return addressRepo.findAllByuser(userId);
	}
	
	@GetMapping(value="/user/cards/")
	public  Iterable<Address> getAllCards(@RequestParam(name = "id") int userId){
		return cardRepo.findAllByuser(userId);
	}
}
