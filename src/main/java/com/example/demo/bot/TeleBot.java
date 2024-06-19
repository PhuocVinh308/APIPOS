package com.example.demo.bot;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.model.Employee;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.utils.jasper.JasperUtils;
import com.example.demo.utils.jasper.ReportType;
import io.github.ndanhkhoi.telegram.bot.annotation.BotRoute;
import io.github.ndanhkhoi.telegram.bot.annotation.CommandDescription;
import io.github.ndanhkhoi.telegram.bot.annotation.CommandMapping;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@BotRoute
@Slf4j
public class TeleBot {
    private static final Long ADMIN_CHAT_ID = 6361994488L;

    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    private AbsSender bot;
    @CommandDescription("Lấy danh sách sản phẩm")
    @CommandMapping(value = "/menu", allowAllUserAccess = true)
    public SendMessage getProducts(Update update) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.HTML); // Đổi sang ParseMode.HTML để sử dụng HTML trong tin nhắn

        if (update != null && update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            try {
                List<Product> products = productService.getNuoc();
                if (products.isEmpty()) {
                    message.setText("Không có sản phẩm nào được tìm thấy.");
                } else {
                    StringBuilder messageText = new StringBuilder();
                    messageText.append("<b>Danh sách sản phẩm:</b>\n\n");
                    for (Product product : products) {
                        messageText.append(String.format("- <b>%s</b>\n  Giá: %s\n  <a href=\"%s\">Hình ảnh</a>\n\n",
                                product.getProductName(), product.getPrice(), product.getLinkImage()));
                    }
                    message.setText(messageText.toString());
                }

                // Set the chatId for the message
                message.setChatId(chatId);
            } catch (Exception e) {
                log.error("Lỗi khi lấy danh sách sản phẩm", e);
                message.setText("Có lỗi xảy ra khi lấy danh sách sản phẩm.");
                // Set the chatId for the message
                message.setChatId(chatId);
            }
        } else {
            log.error("Update không có thông tin Message");
        }

        return message;
    }
    @CommandDescription("Trả hoá đơn đến thời điểm hiện tại")
    @CommandMapping(value = "/thongke", accessUserIds = {6361994488L})
    public SendMessage getInvoice(Update update) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.HTML);

        if (update != null && update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            try {
                LocalDateTime now = LocalDateTime.now();
                LocalDate localDate = now.toLocalDate();
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

                List<OrderDTO> orders = orderService.getThongKeTheoGiaiDoan(sqlDate, sqlDate);
                StringBuilder messageText = new StringBuilder();
                messageText.append("<b>Danh sách đơn hàng:</b>\n\n");
                messageText.append("<code>");
                messageText.append("| ID |   NV   |        TG         |  Tổng |\n");
                messageText.append("|----|--------|-------------------|-------|\n");
                for (OrderDTO order : orders) {
                    messageText.append(String.format("| %d | %s  |%s|  %.0f |\n",
                            order.getId(), order.getEmployeeFullName(),
                            order.getOrderDate(),
                            order.getTotalAmount()));
                }
                messageText.append("</code>");

                message.setText(messageText.toString());
                message.setChatId(chatId);
                message.setParseMode(ParseMode.HTML);

            } catch (Exception e) {
                log.error("Lỗi khi lấy hoá đơn", e);
                message.setText("Có lỗi xảy ra khi lấy hoá đơn.");
                message.setChatId(chatId);
            }
        } else {
            log.error("Update không có thông tin Message");
        }

        return message;
    }
    @CommandDescription("Trả báo cáo dạng PDF")
    @CommandMapping(value = "/baocaopdf", accessUserIds = {6361994488L})
    public void xuatBaoCao(Update update) {
        if (update != null && update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            try {
                List<Map<String, Object>> list = orderService.getXuatExcelMap();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                String formattedDate = now.format(formatter);
                String templatePath = "templates/report/DoanhThu.jasper";
                Map<String, Object> parameters = new HashMap<>();
                Locale locale = new Locale("vi", "VN");
                parameters.put(JRParameter.REPORT_LOCALE, locale);
                parameters.put("NGUOI_XUAT", "Admin");
                parameters.put("NGAY_XUAT", formattedDate);

                ResponseEntity<ByteArrayResource> report = JasperUtils.getReportResponseEntity(templatePath, parameters, list, ReportType.PDF);
                if (report.getBody() != null) {
                    ByteArrayResource resource = report.getBody();
                    InputFile inputFile = new InputFile(resource.getInputStream(), "DoanhThu_" + formattedDate + ".pdf");

                    SendDocument document = new SendDocument();
                    document.setChatId(chatId.toString());
                    document.setDocument(inputFile);

                    bot.execute(document);
                }
            } catch (Exception e) {
                log.error("Lỗi khi tạo và gửi PDF báo cáo", e);
            }
        } else {
            log.error("Update không có thông tin Message");
        }
    }
    public void sendAutoReply(Long chatId, Order order) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        String messageText = generateOrderMessage(order);
        message.setText(messageText);
        message.setParseMode(ParseMode.HTML);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Lỗi khi gửi tin nhắn Telegram", e);
        }
    }
    private String generateOrderMessage(Order order) {
        StringBuilder message = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
       Optional<Employee> nv = employeeService.getEmployeeById(order.getEmployee().getEmployeeId());
        message.append("<b>Thông tin đơn hàng:</b>\n\n");
        message.append("<pre>");
        message.append("Bàn: ").append(order.getBan().getId()).append("\n");
        message.append("SĐT khách hàng: ").append(order.getPhoneNumber()).append("\n");
        message.append("Ngày giờ: ").append(sdf.format(new Date(order.getOrderDate().getTime()))).append("\n");
        message.append("Nhân viên: ").append(nv.get().getFullName()).append("\n");
        message.append("Hình thức thanh toán: ");
        if ("TIEN_MAT".equals(order.getTypePayment())) {
            message.append("Tiền mặt");
        } else if ("CHUYEN_KHOAN".equals(order.getTypePayment())) {
            message.append("Chuyển khoản");
        } else {
            message.append(order.getTypePayment());
        }
        message.append("\n");
        message.append("Tổng tiền: ").append(order.getTotalAmount()).append(" VNĐ\n");
        message.append("</pre>");
        return message.toString();
    }

    @CommandDescription("Trả lời chăm sóc khách hàng VD: /support mở cửa")
    @CommandMapping(value = "/support", allowAllUserAccess = true)
    public void handleSupportCommand(Update update) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.HTML);

        Long chatId = update.getMessage().getChatId();
        String customerQuestion = update.getMessage().getText().trim().toLowerCase();
        String username = update.getMessage().getFrom().getUserName();

        try {
            String replyMessage = "";
            if (customerQuestion.contains("mở cửa")) {
                replyMessage = "Hiện tại quán mở cửa từ 7:00 sáng đến 10:00 tối vào các ngày thường. " +
                        "Vào cuối tuần, quán mở cửa từ 8:00 sáng đến 11:00 tối.";
            } else if (customerQuestion.contains("menu")) {
                replyMessage = "Menu của quán có rất nhiều món ngon như cà phê, trà, nước ép và đồ ăn nhẹ. " +
                        "Bạn có thể tham khảo menu đầy đủ trên website của chúng tôi.";
            } else if (customerQuestion.contains("giá cả")) {
                replyMessage = "Giá cả của các món trong menu thay đổi tùy theo từng loại. " +
                        "Bạn có thể xem giá cả cụ thể khi đến quán hoặc liên hệ với nhân viên phục vụ.";
            } else if (customerQuestion.contains("món đặc biệt")) {
                replyMessage = "Chúng tôi có những món đặc biệt như bánh ngọt và đồ uống đặc biệt theo mùa. " +
                        "Bạn có thể hỏi nhân viên để biết thêm chi tiết.";
            } else if (customerQuestion.contains("wifi")) {
                replyMessage = "Quán có dịch vụ WiFi miễn phí cho khách hàng sử dụng trong thời gian lưu trú tại quán.";
            } else if (customerQuestion.contains("mang đi")) {
                replyMessage = "Chúng tôi cung cấp dịch vụ thức uống mang đi cho khách hàng.";
            } else if (customerQuestion.contains("đỗ xe")||customerQuestion.contains("đậu xe")) {
                replyMessage = "Quán có chỗ đậu xe miễn phí cho khách hàng gần quán.";
            } else if (customerQuestion.contains("sự kiện")) {
                replyMessage = "Chúng tôi tổ chức các sự kiện đặc biệt như sinh nhật, họp mặt,... " +
                        "Bạn có thể tham khảo các sự kiện đang diễn ra trên fanpage của quán.";
            } else if (customerQuestion.contains("khuyến mãi")) {
                replyMessage = "Hiện tại chúng tôi đang có chương trình khuyến mãi cho khách hàng thường xuyên đến quán. " +
                        "Bạn có thể xem chi tiết trên trang web hoặc fanpage của quán.";
            } else if (customerQuestion.contains("ưu đãi")) {
                replyMessage = "Thỉnh thoảng quán có những thời điểm đặc biệt có ưu đãi đặc biệt cho khách hàng. " +
                        "Bạn có thể theo dõi trang fanpage để cập nhật thông tin mới nhất.";
            } else if (customerQuestion.contains("địa chỉ")) {
                replyMessage = "Địa chỉ của quán là: Khu II CTU.";
            } else if (customerQuestion.contains("số điện thoại")) {
                replyMessage = "Số điện thoại liên lạc của quán là: 0901234567.";
            } else if (customerQuestion.contains("email")) {
                replyMessage = "Bạn có thể liên hệ qua email: contact@example.com.";
            } else if (customerQuestion.contains("thắc mắc")) {
                replyMessage = "Để liên hệ khi có thắc mắc về dịch vụ, bạn có thể gọi trực tiếp đến số điện thoại của quán hoặc nhắn tin qua fanpage.";
            } else if (customerQuestion.contains("phản hồi")) {
                replyMessage = "Chúng tôi luôn chào đón phản hồi từ khách hàng để cải thiện chất lượng dịch vụ. " +
                        "Bạn có thể để lại phản hồi trên trang fanpage hoặc gửi email cho chúng tôi.";
            } else if (customerQuestion.contains("bận rộn")) {
                replyMessage = "Thời gian bận rộn nhất của quán thường vào các khung giờ từ 7:30 sáng đến 9:00 sáng và từ 5:00 chiều đến 7:30 tối.";
            } else if (customerQuestion.contains("đặt chỗ")) {
                replyMessage = "Bạn có thể đặt chỗ trước bằng cách gọi điện thoại.";
            } else if (customerQuestion.contains("thanh toán")) {
                replyMessage = "Chúng tôi chấp nhận các phương thức thanh toán như tiền mặt và ví điện tử.";
            } else if (customerQuestion.contains("đặt nước")) {
                replyMessage = "Bạn có thể đặt hàng trực tuyến theo cú pháp sao: /datnuoc <<tên nước>> <<số lượng>> <<SDT lien he>> VD: /datnuoc Trà sua 2 0123456789. Chúng tôi sẽ liên hệ để xác nhận đặt nước!";
            }  else if (customerQuestion.contains("dịch vụ khác")) {
                replyMessage = "Ngoài các dịch vụ thông thường, chúng tôi còn cung cấp dịch vụ tổ chức sự kiện và tiệc nhỏ.";
            } else if (customerQuestion.contains("sản phẩm mới")) {
                replyMessage = "Chúng tôi thường xuyên cập nhật các sản phẩm mới. Bạn có thể theo dõi fanpage để biết thêm chi tiết hoặc liên hệ nhân viên để biết bestseller.";
            } else if (customerQuestion.contains("giờ vàng")) {
                replyMessage = "Quán có giờ vàng giảm giá từ 3:00 chiều đến 5:00 chiều mỗi ngày. Bạn hãy đến và thưởng thức!";
            } else if (customerQuestion.contains("chăm sóc khách hàng")) {
                replyMessage = "Chúng tôi luôn đặt sự hài lòng của khách hàng lên hàng đầu. Mọi thắc mắc và phản hồi đều được chúng tôi xử lý kịp thời.";
            } else if (customerQuestion.contains("thành viên")) {
                replyMessage = "Chúng tôi có chương trình thành viên với nhiều ưu đãi hấp dẫn. Bạn có thể đăng ký tại quán.";
            } else if (customerQuestion.contains("mất đồ")) {
                replyMessage = "Nếu bạn mất đồ tại quán, vui lòng liên hệ nhân viên hoặc gọi số điện thoại của quán để được hỗ trợ.";
            } else if (customerQuestion.contains("liên hệ")) {
                replyMessage = "Bạn có thể liên hệ trực tiếp qua số điện thoại hoặc email: vinh@gmail.com.";
            } else if (customerQuestion.contains("đặt món đặc biệt")) {
                replyMessage = "Bạn có thể đặt món đặc biệt bằng cách liên hệ trực tiếp với nhân viên hoặc đặt trước qua điện thoại.";
            } else if (customerQuestion.contains("thức ăn")) {
                replyMessage = "Quán có phục vụ thức ăn với các món ăn nhẹ và thức uống.";
            } else if (customerQuestion.contains("giao hàng")) {
                replyMessage = "Chúng tôi cung cấp dịch vụ giao hàng. Bạn có thể đặt hàng qua ứng dụng hoặc gọi điện thoại trực tiếp.";
            } else {
                replyMessage = "Cám ơn @" + username + " đã liên hệ với chúng tôi!\nChúng tôi sẽ trả lời bạn sớm nhất có thể.";
                // Gửi tin nhắn từ khách hàng tới admin
                SendMessage messageToAdmin = new SendMessage();
                messageToAdmin.setParseMode(ParseMode.HTML);
                messageToAdmin.setChatId(ADMIN_CHAT_ID.toString());
                messageToAdmin.setText("Tin nhắn từ khách hàng @" + username + ":\n" + customerQuestion.substring("/support".length()).trim());
                bot.execute(messageToAdmin);
            }

            // Gửi tin nhắn trả lời cho khách hàng
            message.setText(replyMessage);
            message.setChatId(chatId.toString());
            bot.execute(message);

        } catch (TelegramApiException e) {
            message.setText("Xin lỗi, có lỗi xảy ra khi xử lý yêu cầu hỗ trợ của bạn.");
            message.setChatId(chatId.toString());
            try {
                bot.execute(message);
            } catch (TelegramApiException ex) {
                message.setText("Xin lỗi, có lỗi xảy ra khi xử lý yêu cầu hỗ trợ của bạn.");
            }
        }
    }


}
