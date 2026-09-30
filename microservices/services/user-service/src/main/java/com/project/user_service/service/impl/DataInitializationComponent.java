package com.project.user_service.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.enums.UserRole;
import com.project.user_service.model.User;
import com.project.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		initializeAdminUser();
	}

    private void initializeAdminUser(){

        String email = "nayaknihaal@gmail.com";
        String password = "nayak2004";

        if (userRepository.findByEmail(email)==null) {
            User adminUser = User.builder()
                            .email(email)
                            .password(passwordEncoder.encode(password))
                            .fullName("nihaal")
                            .role(UserRole.ROLE_SYSTEM_ADMIN)
                            .build();

            User admin = userRepository.save(adminUser);


        }
    }

}
