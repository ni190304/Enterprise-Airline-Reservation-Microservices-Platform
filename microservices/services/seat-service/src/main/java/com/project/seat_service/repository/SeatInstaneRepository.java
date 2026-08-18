package com.project.seat_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.seat_service.model.SeatInstance;

public interface SeatInstaneRepository extends JpaRepository<SeatInstance,Long> {


}
