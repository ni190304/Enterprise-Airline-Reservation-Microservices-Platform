package com.project.user_service.service.impl;

import java.time.LocalDateTime;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.enums.UserRole;
import com.project.payload.dto.UserDTO;
import com.project.payload.response.AuthResponse;
import com.project.user_service.User;
import com.project.user_service.repository.UserRepository;
import com.project.user_service.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signup(UserDTO req) throws Exception {
        User existingUser = userRepository.findByEmail(req.getEmail());

        if (existingUser != null) {
            throw new Exception("email already registered");
        }
        if (req.getRole() == UserRole.ROLE_SYSTEM_ADMIN) {
            throw new Exception("You cannot sign up system admins!");
        }

        User newUser = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(req.getRole())
                .fullName(req.getFullName())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);
        

        return null;
    }

    @Override
    public AuthResponse login(String email, String password) {
        return null;
    }

}
