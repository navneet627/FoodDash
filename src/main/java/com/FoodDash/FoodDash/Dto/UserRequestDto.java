package com.FoodDash.FoodDash.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRequestDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
}
