package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

	private String linkLocal;
	private String linkImage;

	public Product() {
	}

	public Product(Long id, String productName, double price, String linkLocal, String linkURL) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.linkLocal = linkLocal;
		this.linkImage = linkURL;
	}

	public Product(Long id, String productName, double price, String linkImage) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.linkLocal = linkImage;
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

	public void setLinkLocal(String linkLocal) {
		this.linkLocal = linkLocal;
	}



	public String getLinkLocal() {
		return linkLocal;
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
