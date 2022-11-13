package com.raf.reservationservice.mapper;

import com.raf.reservationservice.domain.Hotel;
import com.raf.reservationservice.dto.HotelCreateDto;
import com.raf.reservationservice.dto.HotelDto;
import com.raf.reservationservice.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public Hotel hotelCreateDtoToHotel(HotelCreateDto hotelCreateDto){
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelCreateDto.getHotelName());
        hotel.setCity(hotelCreateDto.getCity());
        hotel.setDescription(hotelCreateDto.getDescription());
        hotel.setManagerId(hotelCreateDto.getManagerId());
        return hotel;
    }

    public HotelDto hotelToHotelDto(Hotel hotel, UserDto userDto){
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setHotelName(hotel.getHotelName());
        hotelDto.setCity(hotel.getCity());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setManager(userDto);
        return hotelDto;
    }

    public Hotel update(Hotel hotel, HotelCreateDto hotelCreateDto){
        hotel.setHotelName(hotelCreateDto.getHotelName());
        hotel.setDescription(hotelCreateDto.getDescription());
        hotel.setCity(hotelCreateDto.getCity());
        hotel.setManagerId(hotelCreateDto.getManagerId());
        return hotel;
    }
}
