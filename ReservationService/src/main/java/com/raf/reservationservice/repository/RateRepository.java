package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Optional<Rate> findRateByClientIdAndHotelId(Long clientId, Long hotelId);
}
