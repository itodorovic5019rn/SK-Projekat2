package com.raf.reservationservice.service;

import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomTypeService {

    RoomType createRoomType(RoomTypeCreateDto roomTypeCreateDto);

    RoomType updateRoomType(RoomTypeCreateDto roomTypeCreateDto, Long id);

    void deleteRoomType(Long id);

    RoomType findRoomTypeById(Long id);

    Page<RoomType> findAllRoomTypes(Pageable pageable);

}
