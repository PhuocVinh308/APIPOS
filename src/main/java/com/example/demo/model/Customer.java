package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "point")
    private double point;

    private String name;

}