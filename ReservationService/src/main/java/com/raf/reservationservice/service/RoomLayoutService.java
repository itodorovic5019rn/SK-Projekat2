package com.raf.reservationservice.service;

import com.raf.reservationservice.domain.Room;
import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RoomDto;
import com.raf.reservationservice.dto.RoomLayoutCreateDto;
import com.raf.reservationservice.dto.RoomLayoutDto;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomLayoutService {

    RoomLayoutDto createRoomLayout(RoomLayoutCreateDto roomLayoutCreateDto);

    RoomLayoutDto updateRoomLayout(RoomLayoutCreateDto roomLayoutCreateDto, Long id);

    void deleteRoomLayout(Long id);

    RoomLayoutDto findRoomLayoutById(Long id);

    Page<RoomLayoutDto> findAllRoomLayouts(Pageable pageable);

    Page<RoomDto> findAllRooms(Pageable pageable);

    RoomDto findRoomById(Long id);

    RoomLayoutDto findRoomLayoutByHotelIdAndRoomTypeId(Long hotelId, Long roomTypeId);

}
