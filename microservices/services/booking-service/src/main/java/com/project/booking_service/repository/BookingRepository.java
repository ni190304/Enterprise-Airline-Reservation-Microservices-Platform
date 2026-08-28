package com.project.booking_service.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.booking_service.model.Booking;
import com.project.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    long countryByFlightInstanceId(Long flightInstanceId);

    @Query(

    """
                        select distinct b from Booking b
                        left join fetch b.passengers p
                        where b.airlineId=JairlineId
                        and (:search is null or LOWER(b.bookingReference) like lower(concat('%',:search, '%') )
                        or lower(p.firstName) like lower(concat('%',:search, '%') )
                        or lower(p. lastName) Like Lower(concat('%',:search, '%') )
                        or lower(p.email) like lower(concat('%', :search, '%') )
                        or lower(b.contactInfo.email) like lower(concat('%',:search, '%') )
                        or lower(b.contactInfo.phone) like lower(concat('%',:search, '%') ))

                        and (:status is null or b.status = :status)
                        and(:flightInstanceId is null or b.flightInstanceId = :flightInstanceId)
            """)
    List<Booking> findByAirlineWithFilter(
            @Param("airlineId") Long airlineId,
            @Param("search") String search,
            @Param("status") BookingStatus status,
            @Param("flightInstanceId") Long flightInstanceId,
            Sort sort);

    boolean existsByBookingReference(String reference);
}
