package com.project.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.user_service.User;

public interface UserRepository extends JpaRepository<User,Long>  {

    User findByEmail(String email);
}
