package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.SignupRequest;
import com.FoodDash.FoodDash.entities.User;
import com.FoodDash.FoodDash.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {

        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhoneNo());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setRole("USER");

        userRepo.save(user);
    }
}
