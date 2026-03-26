package com.FoodDash.FoodDash.service;

import com.FoodDash.FoodDash.Dto.UserRequestDto;
import com.FoodDash.FoodDash.Dto.UserResponseDto;
import com.FoodDash.FoodDash.entities.User;
import com.FoodDash.FoodDash.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto createUser(UserRequestDto dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User savedUser = userRepo.save(user);

        return convertToResponseDto(savedUser);
    }

    private UserResponseDto convertToResponseDto(User user) {

        return  new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole()
        );
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new RuntimeException("invalid User Id :" + id));
        return convertToResponseDto(user);
    }


    public List<UserResponseDto> getAllUser() {
        return userRepo.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
