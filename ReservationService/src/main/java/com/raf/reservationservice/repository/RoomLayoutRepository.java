package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.RoomLayout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomLayoutRepository extends JpaRepository<RoomLayout, Long> {
    Optional<RoomLayout> findRoomLayoutByHotelIdAndRoomTypeId(Long hotelId, Long roomTypeId);
}
