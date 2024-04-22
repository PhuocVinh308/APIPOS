package com.example.demo.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Date orderDate;
    private Double totalAmount;
    private Long banId;
    private String employeeFullName;
}
