package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.Period;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface PeriodRepository extends JpaRepository<Period, Long> {
    Optional<Period> findPeriodByStartDateAndEndDate(String startDate, String endDate);
}
