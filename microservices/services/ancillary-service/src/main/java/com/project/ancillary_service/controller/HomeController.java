package com.project.ancillary_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.payload.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<ApiResponse> HomeController() {
        ApiResponse apiResponse = new ApiResponse("Ancillary services Hello");

        return ResponseEntity.ok(apiResponse);
    }

}
