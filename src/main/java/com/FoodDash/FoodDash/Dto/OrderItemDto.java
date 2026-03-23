package com.FoodDash.FoodDash.Dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private String name;
    private int quantity;
    private double price;

}
