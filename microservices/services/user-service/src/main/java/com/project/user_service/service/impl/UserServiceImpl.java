package com.project.user_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.payload.dto.UserDTO;
import com.project.user_service.mapper.UserMapper;
import com.project.user_service.model.User;
import com.project.user_service.repository.UserRepository;
import com.project.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("user not found with email");
        }

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        
        User user = userRepository.findById(id).orElseThrow(
            () -> new Exception("user not found with id "+id)
        );

        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return UserMapper.toDTOList(users);
    }

}
