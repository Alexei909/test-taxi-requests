package com.example.taxi_request_ws.model;

import com.example.taxi_request_ws.dto.UserResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String name;

    @Column(name = "email")
    private String email;

    public UserResponseDto toUserDto() {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setEmail(this.email);
        return dto;
    }

}
