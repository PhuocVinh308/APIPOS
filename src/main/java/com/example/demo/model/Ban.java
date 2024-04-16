package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ban")
@Entity
public class Ban {
    @Id
	private Long id;
    private boolean status;
	private boolean deleted;
}
