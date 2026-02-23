package com.FoodDash.FoodDash.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MenuItemDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private Long restaurantId;
}
