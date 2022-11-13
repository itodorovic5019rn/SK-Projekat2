package com.raf.reservationservice.dto;

public class ReservationCompleteDto {

    private UserDto client;
    private UserDto manager;

    public ReservationCompleteDto(UserDto client, UserDto manager) {
        this.client = client;
        this.manager = manager;
    }

    public UserDto getClient() {
        return client;
    }

    public void setClient(UserDto client) {
        this.client = client;
    }

    public UserDto getManager() {
        return manager;
    }

    public void setManager(UserDto manager) {
        this.manager = manager;
    }
}
