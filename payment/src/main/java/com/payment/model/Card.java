package com.payment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.json.JSONObject;

@Entity
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String CardNum;
	private int expiryMonth;
	private int expiryYear;
	private String name;
	private CardType cardType;
	private String cvv;
	
	@ManyToOne
	private User user;

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNum() {
		return CardNum;
	}

	public void setCardNum(String cardNum) {
		CardNum = cardNum;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public JSONObject getJson() {
		JSONObject expiry = new JSONObject();
		expiry.put("month", this.getExpiryMonth());
		expiry.put("year", this.getExpiryYear());

		JSONObject json = new JSONObject();
		json.put("cardNum", this.getCardNum());
		json.put("cvv", this.getCvv());
		json.put("holderName", this.getName());
		json.put("cardExpiry", expiry);
		return json;
	}

}
