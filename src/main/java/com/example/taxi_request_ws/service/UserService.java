package com.example.taxi_request_ws.service;

import com.example.taxi_request_ws.dto.UserRequestDto;
import com.example.taxi_request_ws.dto.UserResponseDto;

public interface UserService {

    UserResponseDto addUser(UserRequestDto user);

}
