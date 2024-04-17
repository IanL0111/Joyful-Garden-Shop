package com.joyfulgarden.model.shop;

public class CarItem {
	
	private Products product;
	private Integer quantity;
	
	
	public CarItem() {
		
	}
	
	public CarItem(Products product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
