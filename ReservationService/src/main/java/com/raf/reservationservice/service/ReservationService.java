package com.raf.reservationservice.service;

import com.raf.reservationservice.dto.FreeTermsDto;
import com.raf.reservationservice.dto.ReservationCreateDto;
import com.raf.reservationservice.dto.ReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    ReservationDto createReservation(ReservationCreateDto reservationCreateDto);

    void deleteReservationById(Long id);

    Page<ReservationDto> findAllReservations(Pageable pageable);

    ReservationDto getReservationById(Long id);

    Page<FreeTermsDto> getFreeTerms(Pageable pageable);
}
