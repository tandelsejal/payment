package com.payment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.JSONObject;

import com.payment.Service.HttpRequestService;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String hanldeId;
	private TransactionType transactionType;
	private PaymentType paymentType;
	private double amount;
	private CurrencyCode currencyCode;
	private int responseCode;
	private String paymentHandleToken;
	private long tokenStartTime;
	private long timeToLiveSeconds;
	private String description;
	
	@OneToOne(mappedBy = "payment")
	private Merchant merchant; 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTokenStartTime() {
		return tokenStartTime;
	}

	public void setTokenStartTime(long tokenStartTime) {
		this.tokenStartTime = tokenStartTime;
	}

	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public String getHanldeId() {
		return hanldeId;
	}

	public void setHanldeId(String hanldeId) {
		this.hanldeId = hanldeId;
	}

	public String getPaymentHandleToken() {
		return paymentHandleToken;
	}

	public void setPaymentHandleToken(String paymentHandleToken) {
		this.paymentHandleToken = paymentHandleToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(CurrencyCode currencyCode) {
		this.currencyCode = currencyCode;
	}

	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		json.put("transactionType", this.getTransactionType());
		json.put("paymentType", this.getPaymentType());
		json.put("amount", this.getAmount());
		json.put("currencyCode", this.getCurrencyCode());
		json.put("returnLinks", HttpRequestService.getReturnLinks());
		return json;
	}

}
