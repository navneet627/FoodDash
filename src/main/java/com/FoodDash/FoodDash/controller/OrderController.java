package com.FoodDash.FoodDash.controller;


import com.FoodDash.FoodDash.Dto.OrderDto;
import com.FoodDash.FoodDash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestParam Long userId){
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }


}
