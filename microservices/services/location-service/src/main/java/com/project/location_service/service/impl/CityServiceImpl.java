package com.project.location_service.service.impl;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.project.location_service.service.CityService;
import com.project.payload.request.CityRequest;
import com.project.payload.response.CityResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {@Override
    public CityResponse createCity(CityRequest request) {
        return null;
    }

    @Override
    public CityResponse getCityById(Long id) {
        return null;
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) {
        return null;
    }

    @Override
    public void deleteCity(Long id) {
        
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return null;
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CityResponse> searchCitiesByCountryCode(String countryCode, Pageable pageable) {
        return null;
    }

    @Override
    public boolean cityExists(String cityCode) {
        return false;
    }

    @Override
    public boolean validateCityCode(String cityCode) {
        return false;
    }

}
