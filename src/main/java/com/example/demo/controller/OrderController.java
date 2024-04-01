package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderService;
import com.example.demo.utils.jasper.JasperUtils;
import com.example.demo.utils.jasper.ReportType;
import net.sf.jasperreports.engine.JRParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController( OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderService.getAllOrdersWithPagination(pageable);
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
    }
    @Scheduled(cron = "50 53 15 * * *")
    public void xuatBaoCao() throws IOException {
        ResponseEntity<ByteArrayResource> fileExcel = xuatExcel();
        byte[] excelBytes = fileExcel.getBody().getByteArray();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = now.format(formatter);
        String destinationPath = System.getProperty("user.dir") + File.separator + "report" + File.separator + formattedDate + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(destinationPath);
        outputStream.write(excelBytes);
        outputStream.close();
    }

@GetMapping("/xuatExcel")
    public ResponseEntity<ByteArrayResource> xuatExcel() {
        List<Map<String,Object>> list = orderService.getXuatExcelMap();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = now.format(formatter);

        String templatePath = "templates/report/DoanhThuTemp.jasper";
        Map<String, Object> parameters = new HashMap<>();
        Locale locale = new Locale("vi", "VN");
        parameters.put(JRParameter.REPORT_LOCALE, locale);
        parameters.put("NGUOI_XUAT", "Admin");
        parameters.put("NGAY_XUAT", formattedDate);
        return JasperUtils.getReportResponseEntity(templatePath, parameters, list, ReportType.XLSX);
    }


    @GetMapping("/xuatPDF")
    public ResponseEntity<ByteArrayResource> xuatPDF() {
        List<Map<String,Object>> list = orderService.getXuatExcelMap();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = now.format(formatter);
        String templatePath = "templates/report/DoanhThu.jasper";
        Map<String, Object> parameters = new HashMap<>();
        Locale locale = new Locale("vi", "VN");
        parameters.put(JRParameter.REPORT_LOCALE, locale);
        parameters.put("NGUOI_XUAT", "Admin");
        parameters.put("NGAY_XUAT", formattedDate);
        return JasperUtils.getReportResponseEntity(templatePath, parameters, list, ReportType.PDF);
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
    public Page<OrderDetail> chiTiet(Pageable pageable) {
        Page<Object[]> objPage = orderService.getChiTietHoaDon(pageable);

        Page<OrderDetail> detailPage = objPage.map(objArray -> {
            return new OrderDetail(objArray);
        });

        return detailPage;
    }

    @GetMapping("/thucuongyeuthich")
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
