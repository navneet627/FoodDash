package com.FoodDash.FoodDash.controller;

import com.FoodDash.FoodDash.Dto.AuthRequest;
import com.FoodDash.FoodDash.Dto.AuthResponse;
import com.FoodDash.FoodDash.Dto.SignupRequest;
import com.FoodDash.FoodDash.security.JwtUtil;
import com.FoodDash.FoodDash.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request){

         authService.signup(request);
         return "User Registered Successfully";

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest){
        authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(
                             authRequest.getEmail(),
                             authRequest.getPassword()
                     )
        );

        String token = jwtUtil.generateToken(authRequest.getEmail());

        return new AuthResponse(token);
    }
}
