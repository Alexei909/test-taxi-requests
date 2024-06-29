package com.example.taxi_request_ws.service;

import org.springframework.stereotype.Service;

import com.example.taxi_request_ws.dto.UserRequestDto;
import com.example.taxi_request_ws.dto.UserResponseDto;
import com.example.taxi_request_ws.model.User;
import com.example.taxi_request_ws.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    @Override
    public UserResponseDto addUser(UserRequestDto dto) {
        User user = this.userRepository.save(dto.toUser());
        return user.toUserDto();
    }

}
