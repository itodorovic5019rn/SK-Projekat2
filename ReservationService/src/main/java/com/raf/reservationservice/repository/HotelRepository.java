package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByHotelName(String hotelName);
}
