package com.FoodDash.FoodDash.controller;

import com.FoodDash.FoodDash.Dto.UserRequestDto;
import com.FoodDash.FoodDash.Dto.UserResponseDto;
import com.FoodDash.FoodDash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    ResponseEntity<List<UserResponseDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User with "+ id + "id Deleted");
    }

}
