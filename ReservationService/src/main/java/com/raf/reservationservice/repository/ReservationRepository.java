package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserIdAndRoomIdAndPeriodId(Long userId, Long roomId, Long periodId);

    Optional<Reservation> findReservationByRoomIdAndPeriodId(Long roomId, Long periodId);

    Optional<Reservation> findReservationByRoomId(Long roomId);
}
