package com.project.payload.response;

import com.project.payload.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String jwt;
    private String message;
    private String title;
    private UserDTO user;

}
