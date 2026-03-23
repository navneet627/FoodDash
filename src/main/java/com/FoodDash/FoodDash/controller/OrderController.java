package com.FoodDash.FoodDash.controller;


import com.FoodDash.FoodDash.Dto.OrderDto;
import com.FoodDash.FoodDash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam Long userId){
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

}
