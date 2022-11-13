package com.raf.reservationservice.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class RoomTypeCreateDto {

    @NotBlank
    @Length(min = 1, max = 45)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
