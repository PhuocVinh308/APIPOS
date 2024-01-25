package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "product")
@Entity

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "productName")
	private String productName;
	@Column(name = "price")
	private double price;
    private String linkImage;

	public Product() {
	}
	
	

	public Product(Long id, String productName, double price, String linkImage) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.linkImage = linkImage;
	}



	public String getLinkImage() {
		return linkImage;
	}



	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}



	public Product(Long id, String productName, double price) {
		this.id = id;
		this.productName = productName;
		this.price = price;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public String getProductName() {
		return productName;
	}

	
}
