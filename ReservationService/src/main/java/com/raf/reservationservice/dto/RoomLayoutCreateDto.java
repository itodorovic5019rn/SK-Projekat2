package com.raf.reservationservice.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoomLayoutCreateDto {

    @NotNull
    @Min(0)
    @Max(10000)
    private Integer bottomLimit;
    @NotNull
    @Min(0)
    @Max(10000)
    private Integer topLimit;
    @NotNull
    private Long roomTypeId;
    @NotNull
    private Long hotelId;
    @NotNull
    private Double pricePerDay;

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

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
