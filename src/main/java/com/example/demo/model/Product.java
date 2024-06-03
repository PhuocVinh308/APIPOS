package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "product")
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "price")
	private double price;
	@Column(name = "link_local")
	private String linkLocal;
	@Column(name = "link_image")
	private String linkImage;
 	private boolean is_delete;
	 private String codeDM;
}
