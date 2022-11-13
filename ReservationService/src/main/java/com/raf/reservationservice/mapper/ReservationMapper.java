package com.raf.reservationservice.mapper;

import com.raf.reservationservice.domain.Period;
import com.raf.reservationservice.domain.Reservation;
import com.raf.reservationservice.domain.RoomLayout;
import com.raf.reservationservice.dto.*;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.exception.ServerErrorException;
import com.raf.reservationservice.service.PeriodService;
import com.raf.reservationservice.service.RoomLayoutService;
import com.raf.reservationservice.service.RoomTypeService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ReservationMapper {

    private RoomLayoutService roomLayoutService;
    private PeriodService periodService;

    public ReservationMapper(RoomLayoutService roomLayoutService, PeriodService periodService) {
        this.roomLayoutService = roomLayoutService;
        this.periodService = periodService;
    }

    public Reservation reservationCreateDtoToReservation(ReservationCreateDto reservationCreateDto, DiscountDto discountDto){
        try {
            RoomDto roomDto = roomLayoutService.findRoomById(reservationCreateDto.getRoomId());
            Period period = periodService.getPeriodById(reservationCreateDto.getPeriodId());
            RoomLayoutDto roomLayoutDto = roomLayoutService.findRoomLayoutByHotelIdAndRoomTypeId(roomDto.getHotelDto().getId(), roomDto.getRoomType().getId());
            Double price = roomLayoutDto.getPricePerDay();
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date start = myFormat.parse(period.getStartDate());
            Date end = myFormat.parse(period.getEndDate());
            long diff = end.getTime() - start.getTime();
            int days =(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            price *= days;
            price *= ((double)1-discountDto.getDiscount());

            Reservation reservation = new Reservation();
            reservation.setPeriodId(period.getId());
            reservation.setRoomId(roomDto.getId());
            reservation.setUserId(reservationCreateDto.getUserId());
            reservation.setPrice(price);
            return reservation;
        }catch (NotFoundException e){
            throw e;
        }catch (ParseException e) {
            throw new ServerErrorException("Internal server error");
        }
    }

    public ReservationDto reservationToReservationDto(Reservation reservation, UserDto userDto){
        try {
            RoomDto roomDto = roomLayoutService.findRoomById(reservation.getRoomId());
            Period period = periodService.getPeriodById(reservation.getPeriodId());
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setId(reservation.getId());
            reservationDto.setPeriod(period);
            reservationDto.setRoom(roomDto);
            reservationDto.setUser(userDto);
            reservationDto.setPrice(reservation.getPrice());
            return reservationDto;
        }catch (NotFoundException e){
            throw e;
        }
    }

}
