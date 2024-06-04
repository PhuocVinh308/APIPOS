package com.example.demo.repository;

import com.example.demo.DTO.ComboDto;
import com.example.demo.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComboRepository extends JpaRepository<Combo, Long> {

//    @Query(value = "WITH ItemPairs AS (\n" +
//            "    SELECT \n" +
//            "        oi1.product_id AS item1, \n" +
//            "        oi2.product_id AS item2, \n" +
//            "        COUNT(*) AS support\n" +
//            "    FROM \n" +
//            "        order_items oi1\n" +
//            "    JOIN \n" +
//            "        order_items oi2 ON oi1.order_id = oi2.order_id \n" +
//            "    JOIN \n" +
//            "        product p1 ON oi1.product_id = p1.id\n" +
//            "    JOIN \n" +
//            "        product p2 ON oi2.product_id = p2.id\n" +
//            "    WHERE \n" +
//            "        p1.codeDM = 'THUC_AN' AND p2.codeDM = 'NUOC_UONG'\n" +
//            "    GROUP BY \n" +
//            "        oi1.product_id, oi2.product_id\n" +
//            "),\n" +
//            "TotalOrders AS (\n" +
//            "    SELECT COUNT(*) AS total_orders FROM orders\n" +
//            "),\n" +
//            "ItemCounts AS (\n" +
//            "    SELECT \n" +
//            "        product_id, \n" +
//            "        COUNT(*) AS item_count\n" +
//            "    FROM \n" +
//            "        order_items\n" +
//            "    GROUP BY \n" +
//            "        product_id\n" +
//            ")\n" +
//            "SELECT \n" +
//            "    ip.item1, \n" +
//            "    ip.item2, \n" +
//            "    ip.support,\n" +
//            "    ip.support * 1.0 / tod.total_orders AS full,\n" +
//            "    ip.support * 1.0 / ic1.item_count AS confidence,\n" +
//            "    (ip.support * tod.total_orders * 1.0) / (ic1.item_count * ic2.item_count) AS lift\n" +
//            "FROM \n" +
//            "    ItemPairs ip\n" +
//            "JOIN \n" +
//            "    TotalOrders tod\n" +
//            "JOIN \n" +
//            "    ItemCounts ic1 ON ip.item1 = ic1.product_id\n" +
//            "JOIN \n" +
//            "    ItemCounts ic2 ON ip.item2 = ic2.product_id\n" +
//            "HAVING \n" +
//            "    (full > 0.1) \n" +
//            "    AND (confidence > 0.5) \n" +
//            "    AND (lift > 1)\n" +
//            "ORDER BY \n" +
//            "    ip.support DESC;",nativeQuery = true)
//    List<ComboDto> viewCombo();

        @Query(value = "WITH ItemPairs AS (\n" +
            "    SELECT \n" +
            "        oi1.product_id AS item1, \n" +
            "        oi2.product_id AS item2, \n" +
            "        COUNT(*) AS support\n" +
            "    FROM \n" +
            "        order_items oi1\n" +
            "    JOIN \n" +
            "        order_items oi2 ON oi1.order_id = oi2.order_id \n" +
            "    JOIN \n" +
            "        product p1 ON oi1.product_id = p1.id\n" +
            "    JOIN \n" +
            "        product p2 ON oi2.product_id = p2.id\n" +
            "    WHERE \n" +
            "        p1.codeDM = 'DO_AN' AND p2.codeDM = 'NUOC_UONG'\n" +
            "    GROUP BY \n" +
            "        oi1.product_id, oi2.product_id\n" +
            "),\n" +
            "TotalOrders AS (\n" +
            "    SELECT COUNT(*) AS total_orders FROM orders\n" +
            "),\n" +
            "ItemCounts AS (\n" +
            "    SELECT \n" +
            "        product_id, \n" +
            "        COUNT(*) AS item_count\n" +
            "    FROM \n" +
            "        order_items\n" +
            "    GROUP BY \n" +
            "        product_id\n" +
            ")\n" +
            "SELECT \n" +
            "    ip.item1, \n" +
            "    ip.item2, \n" +
            "    ip.support,\n" +
            "    ip.support * 1.0 / tod.total_orders AS full,\n" +
            "    ip.support * 1.0 / ic1.item_count AS confidence,\n" +
            "    (ip.support * tod.total_orders * 1.0) / (ic1.item_count * ic2.item_count) AS lift\n" +
            "FROM \n" +
            "    ItemPairs ip\n" +
            "JOIN \n" +
            "    TotalOrders tod\n" +
            "JOIN \n" +
            "    ItemCounts ic1 ON ip.item1 = ic1.product_id\n" +
            "JOIN \n" +
            "    ItemCounts ic2 ON ip.item2 = ic2.product_id\n" +
            "HAVING \n" +
            "    (full > 0.1) \n" +
            "    AND (confidence > 0.5) \n" +
            "    AND (lift > 1)\n" +
            "ORDER BY \n" +
            "    ip.support DESC;",nativeQuery = true)
    List<Object[]> viewCombo();




}