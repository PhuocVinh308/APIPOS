package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(orderId, updatedOrder);
    }
    @GetMapping("/max")
    public Object layIDMax(){

        int maxID = orderService.getMaxID();
        return maxID;
//        return new Object() {
//            public int id = maxID;
//        };
    }

    @GetMapping("doanhthungay")
    public Object ketNgay(){
        int doanhThu = orderService.getDoanhThu();

        return new Object(){
            public int tongDoanhThu = doanhThu;
        };
    }

    @GetMapping("/doanhthuthang")
    public List<Object> ketThang() {
        List<Object> dtTheoThang = new ArrayList<>();
        List<Object> doanhThu = orderService.getDoanhThuThang();
        for (Object obj : doanhThu) {

            Object[] objArray = (Object[]) obj;
            int thangValue = (int) objArray[0];
            double tongDoanhThuValue = (double) objArray[1];
            dtTheoThang.add(new Object() {
                public int thang = thangValue;
                public double tongDoanhThu = tongDoanhThuValue;
            });
        }


        return dtTheoThang;
    }


    @GetMapping("/chitiethoadon")
    public List<OrderDetail> chiTiet() {
        List<Object> objList = orderService.getChiTietHoaDon();
        List<OrderDetail> detailList = new ArrayList<>();

        for (Object obj : objList) {
            OrderDetail detail = new OrderDetail(obj);
            detailList.add(detail);
        }

        return detailList;
    }

    @GetMapping("thucuongyeuthich")
    public List<Object> thucUongYeuThich() {
        List<Object> thucUong = new ArrayList<>();
        List<Object> daMua = orderService.getDaMua();
        for (Object obj : daMua) {
            Object[] objArray = (Object[]) obj;
            BigDecimal soluong = (BigDecimal) objArray[0];
            String tenOrder = (String) objArray[1];
            thucUong.add(new Object() {
                public BigDecimal soLuong = soluong;
                public String tenNuoc = tenOrder;
            });
        }
        return thucUong;
    }
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
