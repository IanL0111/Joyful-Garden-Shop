package com.joyfulgarden.model.shop;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orderDetail")
@Component
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderdetailid")
	private Integer orderDetailID;
	@OneToMany
	@JoinColumn(name = "PRODUCTID")
	private List<Products> products;
	@ManyToOne
	@JoinColumn(name="orderid")
	private Order order;
	@Column(name="quantity")
	private Integer quantity;
	
	
	
	public OrderDetail() {
		
	}
	
	



	public OrderDetail(List<Products> products, Order order, Integer quantity) {
		this.products = products;
		this.order = order;
		this.quantity = quantity;
	}





	public Integer getOrderDetailID() {
		return orderDetailID;
	}


	public void setOrderDetailID(Integer orderDetailID) {
		this.orderDetailID = orderDetailID;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public List<Products> getProducts() {
		return products;
	}


	public void setProducts(List<Products> products) {
		this.products = products;
	}
	
	

}
