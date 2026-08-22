package com.project.ancillary_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ancillary_service.model.Ancillary;

public interface AncillaryRepository extends JpaRepository<Ancillary, Long> {

    List<Ancillary> findByAirlineId(Long airlineId);

}
