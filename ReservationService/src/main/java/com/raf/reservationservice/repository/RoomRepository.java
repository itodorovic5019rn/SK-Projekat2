package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> getAllByHotelIdAndTypeId(Long hotelId, Long typeId);
}
