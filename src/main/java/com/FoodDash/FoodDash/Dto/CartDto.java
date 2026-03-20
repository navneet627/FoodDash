package com.FoodDash.FoodDash.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private Long userId;
    private double price;
    private List<CartItemDto> items;
}
