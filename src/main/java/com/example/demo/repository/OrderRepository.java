package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    @Query(value = "SELECT month(o.order_date),SUM(o.total_amount) AS 'Tong doanh thu'\n" +
            "FROM orders o\n" +
            "group by month(o.order_date) order by month(o.order_date) asc",nativeQuery = true)
    List<Object> getDoanhThuThang();
@Query(value = "SELECT SUM(oi.quantity) AS total_quantity,p.product_name\n" +
        "FROM order_items oi\n" +
        "JOIN product p ON p.id = oi.product_id\n" +
        "GROUP BY oi.product_id order by total_quantity desc ",nativeQuery = true)
    List<Object> getDaMua();
@Query(value = "SELECT o.id,p.product_name,oi.quantity,p.price,o.order_date\n" +
        "FROM order_items oi\n" +
        "JOIN product p ON p.id = oi.product_id\n" +
        "JOIN orders o on o.id = oi.order_id\n" +
        "where date(o.order_date) = date(now())",nativeQuery = true)
    List<Object> getXuatExcel();

@Query(value = "SELECT o.id, p.product_name, oi.quantity, p.price, substring(convert(o.order_date, char),12,8) as order_date\n" +
        "FROM order_items oi\n" +
        "JOIN product p ON p.id = oi.product_id\n" +
        "JOIN orders o ON o.id = oi.order_id\n" +
        "WHERE DATE(o.order_date) = DATE(NOW()) order by o.id asc",nativeQuery = true)
List<Map<String,Object>> getXuatExcelMap();
}
