package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Ban")
@Entity
public class Ban {
	@Id
	@GeneratedValue
	private Long id;

    private boolean status;

	private boolean is_deleted;











}
