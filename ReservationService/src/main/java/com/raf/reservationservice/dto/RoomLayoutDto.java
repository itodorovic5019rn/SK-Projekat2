package com.raf.reservationservice.dto;

import com.raf.reservationservice.domain.Hotel;
import com.raf.reservationservice.domain.RoomType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RoomLayoutDto {

    private Long id;
    private Integer bottomLimit;
    private Integer topLimit;
    private RoomType roomType;
    private HotelDto hotelDto;
    private Double pricePerDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBottomLimit() {
        return bottomLimit;
    }

    public void setBottomLimit(Integer bottomLimit) {
        this.bottomLimit = bottomLimit;
    }

    public Integer getTopLimit() {
        return topLimit;
    }

    public void setTopLimit(Integer topLimit) {
        this.topLimit = topLimit;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public HotelDto getHotelDto() {
        return hotelDto;
    }

    public void setHotelDto(HotelDto hotelDto) {
        this.hotelDto = hotelDto;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
