package com.FoodDash.FoodDash.Dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNo;
}
