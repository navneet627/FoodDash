package com.FoodDash.FoodDash.Dto;

import com.FoodDash.FoodDash.entities.OrderItem;
import com.FoodDash.FoodDash.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long orderId;
    private double totalPrice;
    private OrderStatus status;
    private List<OrderItemDto> orderItemList;


}
