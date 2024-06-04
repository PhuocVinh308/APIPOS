package com.example.demo.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String orderDate;
    private Double totalAmount;
    private Long banId;
    private String employeeFullName;
    private String phoneNumber;
    private String typePayment;
}
