package com.project.user_service.service;

import com.project.payload.dto.UserDTO;
import com.project.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;

    AuthResponse signup(UserDTO req) throws Exception;
    
}
