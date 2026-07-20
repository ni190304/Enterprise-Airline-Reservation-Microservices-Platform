package com.project.location_service.mapper;

import com.project.location_service.model.City;
import com.project.payload.request.CityRequest;

public class CityMapper {

    public static City getCity(CityRequest request) {
        if (request == null)
            return null;

        return City.builder()
                .name(request.getName())
                .cityCode(request.getCityCode())
                .countryCode(request.getCountryCode())
                .countryName(request.getCountryName())
                .regionCode(request.getRegionCode())
                .timeZoneId(request.getTimeZoneOffset())
                .build();

    }
}
