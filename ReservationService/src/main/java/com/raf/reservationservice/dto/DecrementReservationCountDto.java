package com.raf.reservationservice.dto;

public class DecrementReservationCountDto {
    private Long userId;

    public DecrementReservationCountDto(Long userId) {
        this.userId = userId;
    }

    public DecrementReservationCountDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
