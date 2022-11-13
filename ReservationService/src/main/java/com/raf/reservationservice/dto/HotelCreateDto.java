package com.raf.reservationservice.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HotelCreateDto {

    @NotBlank
    @Length(min = 2, max = 45)
    private String hotelName;
    @Length(min = 5, max = 150)
    private String description;
    @NotBlank
    @Length(min = 2, max = 45)
    private String city;
    @NotNull
    private Long managerId;

    public HotelCreateDto() {
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}
