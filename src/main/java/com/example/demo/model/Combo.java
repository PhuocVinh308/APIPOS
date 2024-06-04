package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name = "combo")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product food;
    @ManyToOne
    private Product drink;
    @Transient
    private int totalPrice;
    public int getTotalPrice() {
        double total = (food.getPrice() + drink.getPrice()) * 0.9;
        return (int) Math.round(total);
    }}
