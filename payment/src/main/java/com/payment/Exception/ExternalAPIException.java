package com.payment.Exception;

public class ExternalAPIException extends Exception{
	public ExternalAPIException(String string) {
		new Exception(string);
	}
}
