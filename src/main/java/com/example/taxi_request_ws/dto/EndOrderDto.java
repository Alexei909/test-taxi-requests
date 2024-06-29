package com.example.taxi_request_ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EndOrderDto {

    private String taxiDriverName;
    private String status;
    
}
