package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long orderId, Order updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            existingOrder.setBan(updatedOrder.getBan());
            existingOrder.setOrderItems(updatedOrder.getOrderItems());

            return orderRepository.save(existingOrder);
        }

        return null; 
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public int getMaxID() {
        return orderRepository.findMaxByID();
    }

    public List<Object> getChiTietHoaDon() {
       return orderRepository.getOrderDetail();
    }

    public int getDoanhThu() {
        return orderRepository.getDoanhThu();
    }

    public int getDoanhThuThang() {
        return orderRepository.getDoanhThuThang();
    }
}
