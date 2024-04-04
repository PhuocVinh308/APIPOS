package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
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
 	private boolean is_delete;
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
}
