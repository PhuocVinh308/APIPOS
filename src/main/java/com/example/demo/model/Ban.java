package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Ban")
@Entity
public class Ban {
    @Id
	private Long id;

    private boolean status;

	private boolean is_deleted;
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


public Ban( ) {
}

public Ban(Long id, boolean status) {
	super();
	this.id = id;
	this.status = status;
}




public Ban(Long id,boolean status,boolean is_deleted){
		this.id = id;
		this.status =status;
		this.is_deleted = is_deleted;
}
}
