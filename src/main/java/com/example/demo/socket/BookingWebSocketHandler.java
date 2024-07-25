package com.example.demo.socket;
import com.example.demo.model.Product;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@ServerEndpoint("/bookings")
public class BookingWebSocketHandler {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Handle incoming messages from clients (if applicable)
    }

    public void sendBookingNotification(Booking booking) {
        String notification = String.format(
                "Bàn số %d, %d khách, %s: %s",
                booking.getTableNumber(),
                booking.getNumberOfGuests(),
                formatter.format(booking.getBookingTime()),
                booking.getListProduct().stream().map(Product::getProductName).collect(Collectors.joining(", "))
        );

        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(notification);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
