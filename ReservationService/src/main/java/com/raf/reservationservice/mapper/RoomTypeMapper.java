package com.raf.reservationservice.mapper;

import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import org.springframework.stereotype.Component;

@Component
public class RoomTypeMapper {

    public RoomType roomTypeCreateDtoToRoomType(RoomTypeCreateDto roomTypeCreateDto){
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeCreateDto.getName());
        return roomType;
    }

    public RoomType update(RoomType roomType, RoomTypeCreateDto roomTypeCreateDto){
        roomType.setName(roomTypeCreateDto.getName());
        return roomType;
    }
}
