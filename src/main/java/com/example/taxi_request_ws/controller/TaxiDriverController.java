package com.example.taxi_request_ws.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.taxi_request_ws.dto.EndOrderDto;
import com.example.taxi_request_ws.dto.NotificationDto;
import com.example.taxi_request_ws.dto.NotificationToDelete;
import com.example.taxi_request_ws.dto.OrderRequestDto;
import com.example.taxi_request_ws.dto.RequestToOrderDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class TaxiDriverController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/taxi.acceptOrder")
    public void acceptOrder(@Payload OrderRequestDto dto) {
        log.info(dto.getClientName());
        messagingTemplate.convertAndSend(
                String.format("/user/%s/queue/message", dto.getClientName()),
                new NotificationDto(dto.getTaxiDriverName(), "accept"));
    }

    @MessageMapping("/taxi.endOrder")
    public void endOrder(@Payload OrderRequestDto dto) {
        messagingTemplate.convertAndSend(
            String.format("/user/%s/queue/message",dto.getClientName()),
                new EndOrderDto(dto.getTaxiDriverName(), "end"));
    }

    @MessageMapping("/taxi.deleteOrder")
    @SendTo("/topic/taxiDrivers")
    public NotificationToDelete deleteOrder(@Payload RequestToOrderDto dto) {
        return new NotificationToDelete(dto.getClientName(), "delete");
    }
    
}
