package com.payment.Service;

import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.model.Address;
import com.payment.model.Card;
import com.payment.model.Merchant;
import com.payment.model.Transaction;
import com.payment.model.User;
import com.payment.repository.TransactionRepository;

@Service
public class PaymentService {
	@Autowired
	HttpRequestService httpService;
	
	@Autowired
	TransactionRepository transactionRepo;

	private static final String CREATE_HANDLE_URL = "https://api.test.paysafe.com/paymenthub/v1/paymenthandles";
	private static final String PAYMENT_URL = "https://api.test.paysafe.com/paymenthub/v1/payments";

	public Merchant doPaymentHandelRequest(Merchant merchant, User user, Address addr, Card card) {
		HttpURLConnection connection = httpService.getConnection(CREATE_HANDLE_URL);
		if (connection != null) {
			try {
				httpService.writeData(connection, merchant.createPaymentHandelJSON(addr, card));
				JSONObject response = httpService.readResponse(connection);
				merchant.maintainPaymentHandelResponse(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return merchant;
	}

	public String doPayment(Merchant merchant, User user) {
		HttpURLConnection connection = httpService.getConnection(PAYMENT_URL);
		if (connection != null) {
			try {
				httpService.writeData(connection, merchant.createPaymentJSON());
				JSONObject response = httpService.readResponse(connection);
				return maintainPaymentResponse(response, user, merchant);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "Payment Fail...!!!";
	}

	public String maintainPaymentResponse(JSONObject response, User user, Merchant merchant) {
		int responseCode = response.getInt("responseCode");
		String time = response.has("txnTime") ? response.getString("txnTime") : "";
		String status = response.has("status") ? response.getString("status") : "";
		Transaction transaction = new Transaction();
		transaction.setUserId(user.getiId());
		transaction.setMerchantId(merchant.getiId());
		transaction.setTransactionTime(time);
		transaction.setTransactionStatus(status);
		transaction.setTransactionDetail(response.toString());
		transactionRepo.save(transaction);
		
		if (responseCode == 200) {
			if (!status.equals("")) {
				return status;
			}
		}
		return "Payment Fail...!!";
	}
}
