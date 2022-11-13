package com.raf.reservationservice.mapper;

import com.raf.reservationservice.domain.Hotel;
import com.raf.reservationservice.domain.Room;
import com.raf.reservationservice.domain.RoomLayout;
import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.HotelDto;
import com.raf.reservationservice.dto.RoomDto;
import com.raf.reservationservice.dto.RoomLayoutCreateDto;
import com.raf.reservationservice.dto.RoomLayoutDto;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.repository.HotelRepository;
import com.raf.reservationservice.repository.RoomTypeRepository;
import com.raf.reservationservice.service.HotelService;
import com.raf.reservationservice.service.RoomTypeService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.OptionalInt;

@Component
public class RoomLayoutMapper {

    private HotelService hotelService;
    private RoomTypeService roomTypeService;

    public RoomLayoutMapper(HotelService hotelService, RoomTypeService roomTypeService) {
        this.hotelService = hotelService;
        this.roomTypeService = roomTypeService;
    }

    public RoomLayout roomLayoutCreateDtoToRoomLayout(RoomLayoutCreateDto roomLayoutCreateDto){
        RoomLayout roomLayout = new RoomLayout();
        try {
            HotelDto hotelDto = hotelService.findHotelById(roomLayoutCreateDto.getHotelId());
            RoomType roomType = roomTypeService.findRoomTypeById(roomLayoutCreateDto.getRoomTypeId());
        }catch (NotFoundException e){
            throw e;
        }
        roomLayout.setBottomLimit(roomLayoutCreateDto.getBottomLimit());
        roomLayout.setTopLimit(roomLayoutCreateDto.getTopLimit());
        roomLayout.setRoomTypeId(roomLayoutCreateDto.getRoomTypeId());
        roomLayout.setHotelId(roomLayoutCreateDto.getHotelId());
        roomLayout.setPricePerDay(roomLayoutCreateDto.getPricePerDay());
        return roomLayout;
    }

    public RoomLayoutDto roomLayoutToRoomLayoutDto(RoomLayout roomLayout){
        RoomLayoutDto roomLayoutDto = new RoomLayoutDto();
        roomLayoutDto.setId(roomLayout.getId());
        roomLayoutDto.setHotelDto(hotelService.findHotelById(roomLayout.getHotelId()));
        roomLayoutDto.setRoomType(roomTypeService.findRoomTypeById(roomLayout.getRoomTypeId()));
        roomLayoutDto.setBottomLimit(roomLayout.getBottomLimit());
        roomLayoutDto.setTopLimit(roomLayout.getTopLimit());
        roomLayoutDto.setPricePerDay(roomLayout.getPricePerDay());
        return roomLayoutDto;
    }

    public RoomDto roomToRoomDto(Room room){
        RoomDto roomDto = new RoomDto();
        roomDto.setHotelDto(hotelService.findHotelById(room.getHotelId()));
        roomDto.setRoomType(roomTypeService.findRoomTypeById(room.getTypeId()));
        roomDto.setRoomNum(room.getRoomNum());
        roomDto.setId(room.getId());
        return roomDto;
    }

    public RoomLayout update(RoomLayout roomLayout, RoomLayoutCreateDto roomLayoutCreateDto){
        roomLayout.setBottomLimit(roomLayoutCreateDto.getBottomLimit());
        roomLayout.setTopLimit(roomLayoutCreateDto.getTopLimit());
        roomLayout.setHotelId(roomLayoutCreateDto.getHotelId());
        roomLayout.setRoomTypeId(roomLayoutCreateDto.getRoomTypeId());
        roomLayout.setPricePerDay(roomLayoutCreateDto.getPricePerDay());
        return roomLayout;
    }
}
