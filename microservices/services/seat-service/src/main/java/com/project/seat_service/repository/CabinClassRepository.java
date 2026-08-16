package com.project.seat_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.enums.CabinClassType;
import com.project.seat_service.model.CabinClass;

public interface CabinClassRepository extends JpaRepository<CabinClass, Long> {

    List<CabinClass> findByAircraftId(Long aircraftId);

    CabinClass findByAircraftIdAndName(Long aircraftId, CabinClassType name);

    boolean existsByCodeAndAircraftId(String code, Long aircraftId);

    boolean existsByCodeAndAircraftIdAndIdNot(String code, Long aircraftId, Long id);
}
