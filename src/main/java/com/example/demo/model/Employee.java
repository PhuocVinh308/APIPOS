package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "Employee")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long employeeId;
    private String fullName;
    private String cccd;
    private String phoneNumber;
    private String position;
    private String account;
}
