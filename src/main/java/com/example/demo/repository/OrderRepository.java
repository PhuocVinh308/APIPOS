package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  OrderRepository extends JpaRepository<Order, Long> {
 @Query("select MAX(id) from Order")
    int findMaxByID();

    @Query(value = "select o.id,o.order_date,o.total_amount,o.ban_id,p.product_name,oi.quantity,p.price from orders o join order_items oi on oi.order_id = o.id JOIN product p ON p.id = oi.product_id;\n", nativeQuery = true)
     List<Object> getOrderDetail();

    @Query(value = "SELECT  SUM(o.total_amount) AS 'Tong doanh thu'\n" +
            "FROM orders o\n" +
            "WHERE DATE(o.order_date) = DATE(CURRENT_DATE())",nativeQuery = true)
    int getDoanhThu();
    @Query(value = "SELECT  SUM(o.total_amount) AS 'Tong doanh thu'\n" +
            "FROM orders o\n" +
            "WHERE month(o.order_date) = month(CURRENT_DATE()) and year(o.order_date) = year(CURRENT_DATE())",nativeQuery = true)
    int getDoanhThuThang();
}
