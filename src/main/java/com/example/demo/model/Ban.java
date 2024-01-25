package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "Ban")
@Entity
public class Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean status;

//    @OneToMany(mappedBy = "ban", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Order> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

//	public List<Order> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
//
//	public Ban(Long id, boolean status, List<Order> orders) {
//		this.id = id;
//		this.status = status;
//		this.orders = orders;
//	}



public Ban() {
}

public Ban(Long id, boolean status) {
	super();
	this.id = id;
	this.status = status;
}

}
