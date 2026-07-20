package com.project.location_service.service;

import org.springframework.data.domain.Page;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;

import com.project.payload.request.CityRequest;
import com.project.payload.response.CityResponse;

public interface CityService {

    CityResponse createCity(CityRequest request);
    CityResponse getCityById(Long id);

    CityResponse updateCity(Long id, CityRequest request);
    void deleteCity(Long id);
    Page<CityResponse> getAllCities(Pageable pageable);

    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> searchCitiesByCountryCode(String countryCode, Pageable pageable);

    boolean cityExists(String cityCode);
    boolean validateCityCode(String cityCode);

}
