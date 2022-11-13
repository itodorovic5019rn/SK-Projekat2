package com.raf.reservationservice.mapper;

import com.raf.reservationservice.domain.Hotel;
import com.raf.reservationservice.domain.Rate;
import com.raf.reservationservice.dto.HotelDto;
import com.raf.reservationservice.dto.RateCreateDto;
import com.raf.reservationservice.dto.RateDto;
import com.raf.reservationservice.dto.UserDto;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.repository.HotelRepository;
import com.raf.reservationservice.service.HotelService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RateMapper {

    private HotelService hotelService;


    public RateMapper(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public Rate rateCreateDtoToRate(RateCreateDto rateCreateDto) {
        Rate rate = new Rate();
        try {
            HotelDto hotel = hotelService.findHotelById(rateCreateDto.getHotelId());
        }catch (NotFoundException e){
            throw e;
        }

        rate.setRate(rateCreateDto.getRate());
        rate.setClientId(rateCreateDto.getClientId());
        rate.setComment(rateCreateDto.getComment());
        rate.setHotelId(rateCreateDto.getHotelId());

        return rate;
    }

    public RateDto rateToRateDto(Rate rate, UserDto userDto){
        try {
            RateDto rateDto = new RateDto();
            rateDto.setId(rate.getId());
            rateDto.setComment(rate.getComment());
            rateDto.setRate(rate.getRate());
            rateDto.setClient(userDto);

            HotelDto hotel = hotelService.findHotelById(rate.getHotelId());

            rateDto.setHotel(hotel);
            return rateDto;
        }catch (NotFoundException e){
            throw e;
        }

    }

    public Rate updateRate(RateCreateDto rateCreateDto, Rate rate){
        try {
            HotelDto hotel = hotelService.findHotelById(rateCreateDto.getHotelId());

            rate.setComment(rateCreateDto.getComment());
            rate.setRate(rateCreateDto.getRate());
            rate.setClientId(rateCreateDto.getClientId());
            rate.setHotelId(rateCreateDto.getHotelId());
            return rate;
        }catch (NotFoundException e){
            throw e;
        }
    }

}
