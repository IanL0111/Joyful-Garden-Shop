package com.joyfulgarden.model.shop;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orderTable")
@Component
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="orderid")
	private Integer orderID;
	@Column(name="orderdate")
	private Date orderDate;
	@Column(name="receivingmethod")
	private String receivingMethod;
	@Column(name="deliverydate")
	private Date deliveryDate;
	@Column(name="receiver")
	private String receiver;
	@Column(name="contectPhone")
	private String contectPhone;
	@Column(name="address")
	private String address;
	@Column(name="remark")
	private String remark;
	@Column(name="deliveryfee")
	private Integer deliveryFee;
	@Column(name="amount")
	private Integer amount;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetail> orderDetail;
	
	
	public Order () {
		
	}
	
	

	public Order(Date orderDate, String receivingMethod, Date deliveryDate, String receiver,
			String contectPhone, String address, String remark, Integer deliveryFee, Integer amount) {
		this.orderDate = orderDate;
		this.receivingMethod = receivingMethod;
		this.deliveryDate = deliveryDate;
		this.receiver = receiver;
		this.contectPhone = contectPhone;
		this.address = address;
		this.remark = remark;
		this.deliveryFee = deliveryFee;
		this.amount = amount;
	}

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getReceivingMethod() {
		return receivingMethod;
	}

	public void setReceivingMethod(String receivingMethod) {
		this.receivingMethod = receivingMethod;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContectPhone() {
		return contectPhone;
	}

	public void setContectPhone(String contectPhone) {
		this.contectPhone = contectPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Integer deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
