package com.raf.reservationservice.repository;

import com.raf.reservationservice.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    Optional<RoomType> findByName(String name);
}
