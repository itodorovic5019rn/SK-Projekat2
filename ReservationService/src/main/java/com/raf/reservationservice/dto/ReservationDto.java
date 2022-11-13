package com.raf.reservationservice.dto;

import com.raf.reservationservice.domain.Period;

public class ReservationDto {

    private Long id;
    private UserDto user;
    private RoomDto room;
    private Period period;
    private Double price;

    public ReservationDto() {
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
