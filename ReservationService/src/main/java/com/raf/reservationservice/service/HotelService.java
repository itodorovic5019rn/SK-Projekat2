package com.raf.reservationservice.service;

import com.raf.reservationservice.domain.Hotel;
import com.raf.reservationservice.dto.HotelCreateDto;
import com.raf.reservationservice.dto.HotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    Page<HotelDto> findAllHotels(Pageable pageable);

    HotelDto updateHotel(HotelCreateDto hotelCreateDto, Long id);

    HotelDto createHotel(HotelCreateDto hotelCreateDto);

    void deleteHotelById(Long id);

    HotelDto findHotelById(Long id);
}
