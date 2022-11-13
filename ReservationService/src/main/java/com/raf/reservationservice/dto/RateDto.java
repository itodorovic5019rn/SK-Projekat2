package com.raf.reservationservice.dto;

import javax.persistence.Column;

public class RateDto {

    private Long id;
    private Double rate;
    private String comment;
    private UserDto client;
    private HotelDto hotel;

    public RateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserDto getClient() {
        return client;
    }

    public void setClient(UserDto client) {
        this.client = client;
    }

    public HotelDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelDto hotel) {
        this.hotel = hotel;
    }
}
