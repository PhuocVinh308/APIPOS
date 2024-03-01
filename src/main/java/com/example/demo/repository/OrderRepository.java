package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface  OrderRepository extends JpaRepository<Order, Long> {
//    @Query("SELECT  SUM(o.total_amount)\n" +
//            "FROM order_items oi\n" +
//            "JOIN orders o ON oi.order_id = o.id\n" +
//            "JOIN product p ON p.id = oi.product_id\n" +
//            "WHERE o.order_date > '2024-01-01'and o.order_date <'2024-12-31'\n" +
//            "GROUP BY p.id;")
//    public Long thongKeHoaDon()
}
