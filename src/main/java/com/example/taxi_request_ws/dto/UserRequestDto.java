package com.example.taxi_request_ws.dto;

import com.example.taxi_request_ws.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {

    private String name;
    private String email;

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);   
        return user;
    }

}
