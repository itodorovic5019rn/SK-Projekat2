package com.raf.reservationservice.service;

import com.raf.reservationservice.domain.Rate;
import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RateCreateDto;
import com.raf.reservationservice.dto.RateDto;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RateService {

    RateDto createRate(RateCreateDto rateCreateDto);

    RateDto updateRate(RateCreateDto rateCreateDto, Long id);

    void deleteRate(Long id);

    RateDto findRateById(Long id);

    Page<RateDto> findAllRates(Pageable pageable);
}
