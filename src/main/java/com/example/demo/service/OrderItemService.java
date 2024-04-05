package com.example.demo.service;

import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Transactional
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList;

    }

    public OrderItem getOrderItemById(Long id) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);
        return orderItemOptional.orElse(null);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    public void deleteOrderItem(Long id){
        orderItemRepository.deleteById(id);
    }


}
