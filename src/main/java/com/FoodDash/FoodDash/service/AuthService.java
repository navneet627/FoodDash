package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.AuthRequest;
import com.FoodDash.FoodDash.Dto.AuthResponse;
import com.FoodDash.FoodDash.Dto.SignupRequest;
import com.FoodDash.FoodDash.entities.User;
import com.FoodDash.FoodDash.enums.Role;
import com.FoodDash.FoodDash.repository.UserRepo;
import com.FoodDash.FoodDash.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

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
        user.setRole(Role.ROLE_USER);

        String token = jwtUtil.generateToken(user);

        userRepo.save(user);
    }

    public AuthResponse login(AuthRequest authRequest) {

        User user = userRepo.findByEmail(authRequest.getEmail()).orElseThrow(()->
                new RuntimeException("user not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token);

    }
}
