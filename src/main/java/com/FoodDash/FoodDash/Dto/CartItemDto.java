package com.FoodDash.FoodDash.Dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long menuItemId;
    private String name;
    private int quantity;
    private double price;
}
