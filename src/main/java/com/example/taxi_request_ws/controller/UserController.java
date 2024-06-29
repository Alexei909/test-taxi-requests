package com.example.taxi_request_ws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.taxi_request_ws.dto.RequestToOrderDto;
import com.example.taxi_request_ws.dto.UserRequestDto;
import com.example.taxi_request_ws.dto.UserResponseDto;
import com.example.taxi_request_ws.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @MessageMapping("/user.addUser")
    public ResponseEntity<UserResponseDto> addUser(@Payload UserRequestDto dto) {
        UserResponseDto user = this.userService.addUser(dto);
        log.info("User {} created", user.getName());
        return ResponseEntity.ok(user);
    }

    @MessageMapping("/user.makeOrder")
    @SendTo("/topic/taxiDrivers")
    public RequestToOrderDto makeOrder(@Payload RequestToOrderDto dto) {
        return dto;
    }
}
