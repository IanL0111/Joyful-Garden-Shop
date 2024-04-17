package com.joyfulgarden.model.shop;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Products")
@Component
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCTID")
	private Integer productID;
	@Column(name="PRODUCTNAME")
	private String	productName; 
	@Column(name="PRICE")
	private Integer price;
	@Column(name="STOCK")
	private Integer stock;
	@ManyToOne
    @JoinColumn(name = "CATEGORYID")
	private CategoriesP category;
	@Column(name="IMAGE")
	private String image;
	
	
	public Products() {
		
	}
	
	
	
	public Products(Integer productID, String productName, Integer price, Integer stock, CategoriesP category,
			String image) {
		this.productID = productID;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.image = image;
	}

	


	public Products(String productName, Integer price, Integer stock, CategoriesP category, String image) {
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.image = image;
	}


	public Integer getProductID() {
		return productID;
	}
	public void setProductID(Integer productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public CategoriesP getCategory() {
		return category;
	}
	public void setCategory(CategoriesP category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	

}
