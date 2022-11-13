package com.raf.reservationservice.dto;

import com.raf.reservationservice.domain.Period;

public class FreeTermsDto {

    private RoomLayoutDto roomLayoutDto;
    private Integer numberOfFreeRooms;
    private Period period;

    public FreeTermsDto() {
    }



    public RoomLayoutDto getRoomLayoutDto() {
        return roomLayoutDto;
    }

    public void setRoomLayoutDto(RoomLayoutDto roomLayoutDto) {
        this.roomLayoutDto = roomLayoutDto;
    }

    public Integer getNumberOfFreeRooms() {
        return numberOfFreeRooms;
    }

    public void setNumberOfFreeRooms(Integer numberOfFreeRooms) {
        this.numberOfFreeRooms = numberOfFreeRooms;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
