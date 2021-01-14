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
public class Merchant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int iId;
	private String sAccountId;
	private String merchantRefNum;
	
	@OneToOne (mappedBy="merchant")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "id")
	private Payment payment;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getMerchantRefNum() {
		return merchantRefNum;
	}

	public void setMerchantRefNum(String merchantRefNum) {
		this.merchantRefNum = merchantRefNum;
	}

	public int getiId() {
		return iId;
	}

	public void setiId(int iId) {
		this.iId = iId;
	}

	public String getsAccountId() {
		return sAccountId;
	}

	public void setsAccountId(String sAccountId) {
		this.sAccountId = sAccountId;
	}

	public boolean doHaveValidToken(long currentTimeMillis) {
		if( ( currentTimeMillis - this.getPayment().getTokenStartTime() ) < (this.getPayment().getTimeToLiveSeconds() * 1000 ) ) {
			return true;
		}
		return false;
	}

	public String createPaymentHandelJSON(Address addr, Card card) {
		JSONObject json = this.getPayment().getJson();
		json.put("merchantRefNum", this.getMerchantRefNum());
		json.put("card", card.getJson());
		json.put("billingDetails", addr.getJson());
		json.put("returnLinks", HttpRequestService.getReturnLinks());
		return json.toString();
	}

	public void maintainPaymentHandelResponse(JSONObject response) {
		this.getPayment().setResponseCode(response.getInt("responseCode"));
		if( this.getPayment().getResponseCode() == 200) {
			this.getPayment().setTokenStartTime(System.currentTimeMillis());
			if(response.has("id")) {
				this.getPayment().setHanldeId(response.getString("id"));
			}
			if(response.has("paymentHandleToken")) {
				this.getPayment().setPaymentHandleToken(response.getString("paymentHandleToken"));
			}
			if(response.has("timeToLiveSeconds")) {
				this.getPayment().setTimeToLiveSeconds(response.getInt("timeToLiveSeconds"));
			}
		}
	}

	public String createPaymentJSON() {
		JSONObject json = new JSONObject();
		json.put("merchantRefNum", this.getMerchantRefNum());
		json.put("amount", this.getPayment().getAmount());
		json.put("currencyCode", this.getPayment().getCurrencyCode());
		json.put("description", this.getPayment().getDescription());
		json.put("dupCheck", true);
		json.put("settleWithAuth", false);
		json.put("paymentHandleToken", this.getPayment().getPaymentHandleToken());
		return json.toString();
	}
	
}
