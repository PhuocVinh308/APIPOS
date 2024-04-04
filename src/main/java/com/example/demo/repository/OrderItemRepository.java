package com.example.demo.repository;

import com.example.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface  OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "DELETE FROM Order_item WHERE order_id IN (SELECT id FROM Orders WHERE ban_id = :banId)\n",nativeQuery = true)
    @Modifying
    void deleteBanById(Long banId);
}
