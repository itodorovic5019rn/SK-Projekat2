package com.raf.reservationservice.service;

import com.raf.reservationservice.domain.Period;
import com.raf.reservationservice.dto.PeriodCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeriodService {

    Page<Period> findAllPeriods(Pageable pageable);

    Period addPeriod(PeriodCreateDto periodCreateDto);

    void deletePeriod(Long id);

    Period getPeriodById(Long id);

    Period updatePeriod(PeriodCreateDto periodCreateDto, Long id);
}
