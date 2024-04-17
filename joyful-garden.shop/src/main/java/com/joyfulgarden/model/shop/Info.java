package com.joyfulgarden.model.shop;

public class Info {
    private PaymentUrl paymentUrl;
    private long transactionId;
    private String paymentAccessToken;
	public PaymentUrl getPaymentUrl() {
		return paymentUrl;
	}
	public void setPaymentUrl(PaymentUrl paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getPaymentAccessToken() {
		return paymentAccessToken;
	}
	public void setPaymentAccessToken(String paymentAccessToken) {
		this.paymentAccessToken = paymentAccessToken;
	}

    
}
