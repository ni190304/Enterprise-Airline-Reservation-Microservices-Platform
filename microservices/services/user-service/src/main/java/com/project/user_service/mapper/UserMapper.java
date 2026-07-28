package com.project.user_service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.project.payload.dto.UserDTO;
import com.project.user_service.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {

        if (user == null)
            return null;

        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .build();

    }

    public static List<UserDTO> toDTOList(List<User> users) {

        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());

    }
}
