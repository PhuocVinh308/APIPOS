package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @GetMapping("/xuatExcel")
    public void xuatExcel(HttpServletResponse response) throws IOException {
        List<Object> orderDetails = orderService.getXuatExcel();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = now.format(formatter);
        String sheetName = "Doanhthu_" + formattedDate.replace(':', '_');

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã hoá đơn");
        headerRow.createCell(1).setCellValue("Tên sản phẩm");
        headerRow.createCell(2).setCellValue("Số luợng");
        headerRow.createCell(3).setCellValue("Giá");
        headerRow.createCell(4).setCellValue("Thời gian");

        int rowNum = 1;
        for (Object obj : orderDetails) {
            Row row = sheet.createRow(rowNum++);
            Object[] objArray = (Object[]) obj;
            row.createCell(0).setCellValue((long) objArray[0]);
            row.createCell(1).setCellValue((String) objArray[1]);
            row.createCell(2).setCellValue((int) objArray[2]);
            row.createCell(3).setCellValue((double) objArray[3]);
            if (objArray[4] instanceof Timestamp) {
                Timestamp timestamp = (Timestamp) objArray[4];
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                row.createCell(4).setCellValue(formattedTime);
            } else if (objArray[4] instanceof String) {
                LocalDateTime dateTime = LocalDateTime.parse((String) objArray[4]);
                String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                row.createCell(4).setCellValue(formattedTime);
            }



        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
        // Đặt các header cho file Excel
        response.setHeader("Content-Disposition", "attachment; filename="+ sheetName+".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Ghi workbook vào HttpServletResponse
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    @GetMapping("/doanhthungay")
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
