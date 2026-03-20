package com.FoodDash.FoodDash.controller;

import com.FoodDash.FoodDash.Dto.CartDto;
import com.FoodDash.FoodDash.service.CartSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartSerivce cartSerivce;

    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestParam Long userId,
                                      @RequestParam Long menuItemId,
                                      @RequestParam int quantity){
        return ResponseEntity.ok(cartSerivce.addtoCart(userId,menuItemId,quantity));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartSerivce.getCart(userId));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeItem(@RequestParam Long userId,
                                              @RequestParam Long menuItemId){
        cartSerivce.removeItem(userId,menuItemId);

        return  ResponseEntity.ok("Item Removed");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId){
        cartSerivce.clearCart(userId);

        return ResponseEntity.ok("Cart Cleared");
    }


}
