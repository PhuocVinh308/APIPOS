package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Table(name = "employee")
@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long employeeId;

    @Column(name = "full_name")
    private String fullName;

    private String cccd;
    @Column(name = "phone_number")
    private String phoneNumber;

    private String position;
    @Column(unique = true)
    private String account;
    private String dob;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;
    @Column(name = "is_deleted")
    private boolean isDeleted=false;
    private int salary;
    private String imageProfile;
}
