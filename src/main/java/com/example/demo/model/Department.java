package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name_department")
    private String nameDepartment;
    @Column(name = "full_address")
    private String fullAddress;
    private String longitude;
    private String latitude;
    @OneToOne
    private Employee manager;
}
