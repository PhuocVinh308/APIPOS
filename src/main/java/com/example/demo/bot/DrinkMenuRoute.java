package com.example.demo.bot;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.model.Product;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import io.github.ndanhkhoi.telegram.bot.annotation.BotRoute;
import io.github.ndanhkhoi.telegram.bot.annotation.CommandDescription;
import io.github.ndanhkhoi.telegram.bot.annotation.CommandMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@BotRoute
@Slf4j
public class DrinkMenuRoute {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

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
    @CommandMapping(value = "/thongke", allowAllUserAccess = true)
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
}
