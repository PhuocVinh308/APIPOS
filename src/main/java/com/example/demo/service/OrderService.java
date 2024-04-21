package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }



    public Page<Order> getAllOrdersWithPagination(Pageable pageable) {
        return orderRepository.getAllOrdersWithPagination(pageable);
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

    public  List<Object> getChiTietHoaDon() {
        return orderRepository.getChiTietHoaDon();
    }
    public int getDoanhThu() {
int temp = orderRepository.getDoanhThu();
return orderRepository.getDoanhThu();

    }

    public List<Object> getDoanhThuThang() {
        return orderRepository.getDoanhThuThang();
    }

    public List<Object> getDaMua() {
        return orderRepository.getDaMua();
    }

    public List<Object> getXuatExcel() {
   return orderRepository.getXuatExcel();
    }

    public List<Map<String,Object>> getXuatExcelMap() {
        return orderRepository.getXuatExcelMap();
    }


    public Order getThongKeTheoGiaiDoan(Date start, Date end) {
        Order result = orderRepository.getThongKeTheoGiaiDoan(start, end);
       return result;
    }

}
