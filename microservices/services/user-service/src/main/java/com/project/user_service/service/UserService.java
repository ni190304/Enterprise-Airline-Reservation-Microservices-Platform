package com.project.user_service.service;

import java.util.List;

import com.project.payload.dto.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email) throws Exception;
    UserDTO getUserById(Long id) throws Exception;
    List<UserDTO> getAllUsers();
}
