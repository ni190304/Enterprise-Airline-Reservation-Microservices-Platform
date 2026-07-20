package com.project.location_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.payload.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/")
    public ApiResponse HomeController() {

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("hello everyone im location service");

        return apiResponse;

    }
}
