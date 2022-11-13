package com.raf.reservationservice.dto;

import javax.persistence.Column;
import java.util.Date;

public class HotelDto {

    private Long id;
    private String hotelName;
    private String description;
    private String city;
    private UserDto manager;

    public HotelDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserDto getManager() {
        return manager;
    }

    public void setManager(UserDto manager) {
        this.manager = manager;
    }
}
