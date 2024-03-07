package com.example.demo.model;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class OrderDetail {

    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    private Double totalAmount;
    private Long banId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String phoneNumber;

    public OrderDetail(Object obj) {
        Object[] data = (Object[]) obj;
        this.id = (Long) data[0];
        this.orderDate = (Date) data[1];
        this.totalAmount = (Double) data[2];
        this.banId = (Long) data[3];
        this.productName = (String) data[4];
        this.quantity = (Integer) data[5];
        this.price = (Double) data[6];
        this.phoneNumber = (String) data[7];
    }
}
